import MasterWorkerProtocol.IsWorkCompleted
import abstraccions.{Air, Water}
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
      if(z == 2 && y == 2 && x == 2)
        Water(z, y, x)
      else if (z == 1 && y == 1 && x == 1)
        Water(z, y, x)
      else
        Air(z, y, x)
    }
    Matrix(coordinates.toArray)
  }



  "Worker" should {
    "work" in {

      val listOfMatrix : Seq[Matrix] = Seq(MatrixMock(), MatrixMock(), MatrixMock(), MatrixMock(), MatrixMock())

      val system = new System(FloodFill, listOfMatrix, "output")
      system.addWorkers(2)
      waitAllWorkIsDone(system.resultHandler)
    }
  }

  def waitAllWorkIsDone(resultHandler: ActorRef): Unit = {
    while (Await.result(resultHandler ? IsWorkCompleted, 1.minute).asInstanceOf[Boolean] == false) {
      Thread.sleep(500)
    }
  }
}
