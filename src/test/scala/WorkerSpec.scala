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


  def MatrixMock(): Matrix = {
    val coordinates = for(
      z <- 0 to 3;
      y <- 0 to 3;
      x <- 0 to 3
    ) yield {
      if(z == 0 && y == 0 && x == 0)
        Coordinate(z, y, x, 1)
      else if (z == 1 && y == 1 && x == 1)
        Coordinate(z, y, x, 1)
      else
        Coordinate(z, y, x)
    }
    Matrix(coordinates.toArray)
  }



  "Worker" should {
    "work" in {

      val listOfMatrix : Seq[Matrix] = Seq(MatrixMock())

      val system = new System(DegreeAlgorithm, listOfMatrix, "output")
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
