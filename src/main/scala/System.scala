import MasterWorkerProtocol.{AllWorkSent, WorkerCreated}
import akka.actor.{ActorSystem, Props}

class System(algorithm: AbstractAlgorithm, works: Seq[AbstractInput], outputFilename: String) {
  val system = ActorSystem("System")
  val resultHandler = system.actorOf(Props(new ResultHandler(outputFilename)), "resultHandler")
  val master = system.actorOf(Props(new Master(algorithm, resultHandler)), "master")

  works.foreach(master ! _)
  master ! AllWorkSent

  def addWorkers(num: Int): Unit = {
    for (i <- 1 to num) {
      master ! WorkerCreated(createWorker(works))
    }
  }

  private def createWorker(works : Seq[AbstractInput]) = system.actorOf(Props(new BatchWorker(new Batch(works), master.path)))
}
