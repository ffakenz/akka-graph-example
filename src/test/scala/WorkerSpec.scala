import MasterWorkerProtocol.IsWorkCompleted
import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}
import akka.pattern.ask
import scala.concurrent.duration._

import concurrent.Await

class WorkerSpec extends TestKit(ActorSystem("WorkerSpec"))
with ImplicitSender
with WordSpecLike
with BeforeAndAfterAll
with MustMatchers {

  implicit val timeout = Timeout(5 seconds)

  override def afterAll() {
    system.shutdown()
  }

  "Worker" should {
    "work" in {

      val x, y, z = 5
      var matrix = Array.ofDim[Int](x,y,z)

      matrix(0)(0)(0) = 1//we initialize the first water drop
      println("0,0,0 is: " + matrix(0)(0)(0))

      var a : Seq[Chunk] = Seq()
      a = a:+(Chunk(matrix))
      a = a:+(Chunk(matrix))
      //val inputs = (1 to 10) map { Coordinate(_) }

      val system = new System(DegreeAlgorithm, a, "output")
      system.addWorkers(1)
      waitAllWorkIsDone(system.resultHandler)
    }
  }

  def waitAllWorkIsDone(resultHandler: ActorRef): Unit = {
    while (Await.result(resultHandler ? IsWorkCompleted, 1.minute).asInstanceOf[Boolean] == false) {
      Thread.sleep(500)
    }
  }
}
