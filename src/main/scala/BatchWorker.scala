import MasterWorkerProtocol.Result
import akka.actor.{ActorPath, ActorRef}
import akka.pattern.pipe

import concurrent.Future

class BatchWorker(batch: Batch, masterLocation: ActorPath) extends Worker(batch, masterLocation) {
  // We'll use the current dispatcher for the execution context.
  // You can use whatever you want.
  implicit val ec = context.dispatcher

  def doWork(workSender: ActorRef, msg: AbstractInput): Unit = {
    Future {
      val algResult = algorithm.flatMap(_.execute(batch, msg))

      algResult match {
        case None =>
        case _ => workSender ! Result(msg, algResult)
      }

      WorkComplete("done")
    } pipeTo self
  }
}
