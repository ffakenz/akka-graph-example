import akka.actor.{ActorRef, Actor, ActorLogging, ActorPath}

abstract class Worker(masterLocation: ActorPath)
  extends Actor with ActorLogging {
  import MasterWorkerProtocol._

  var algorithm: Option[AbstractAlgorithm] = None

  // We need to know where the master is
  val master = context.actorSelection(masterLocation)

  // This is how our derivations will interact with us.  It
  // allows derivations to complete work asynchronously
  case class WorkComplete(result: Any)

  // Required to be implemented
  def doWork(workSender: ActorRef, work: AbstractInput): Unit

  // Notify the Master that we're alive
  override def preStart() = master ! WorkerCreated(self)

  // This is the state we're in when we're working on something.
  // In this state we can deal with messages in a much more
  // reasonable manner
  def working(work: AbstractInput): Receive = {
    // Pass... we're already working
    case WorkIsReady =>
    // Pass... we're already working
    case NoWorkToBeDone =>
    // Pass... we shouldn't even get this
    case WorkToBeDone(_) =>
      log.error("Yikes. Master told me to do work, while I'm working.")
    // Our derivation has completed its task
    case WorkComplete(result) =>
      log.info("Work is complete.  Result {}.", result)
      master ! WorkIsDone(self)
      master ! WorkerRequestsWork(self)
      // We're idle now
      context.become(idle)
  }

  // In this state we have no work to do.  There really are only
  // two messages that make sense while we're in this state, and
  // we deal with them specially here
  def idle: Receive = {
    case DoAlgorithm(alg) => algorithm = Some(alg)

    // Master says there's work to be done, let's ask for it
    case WorkIsReady =>
      log.info("Requesting work")
      master ! WorkerRequestsWork(self)
    // Send the work off to the implementation
    case WorkToBeDone(work) => algorithm match {
      case None => log.error("Got work before algorithm was received.")
      case _ =>
        log.info("Got work {}", work)
        doWork(sender(), work)
        context.become(working(work))
    }
    // We asked for it, but either someone else got it first, or
    // there's literally no work to be done
    case NoWorkToBeDone =>
  }

  def receive = idle
}
