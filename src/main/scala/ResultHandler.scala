import MasterWorkerProtocol.{AllWorkCompleted, IsWorkCompleted, Result}
import akka.actor.Actor

class ResultHandler(filename: String) extends Actor {
  val outfile = new java.io.PrintWriter(filename)
  var isWorkCompleted = false

  def receive = {
    case Result(input, result) => outfile.println( " INPUT: " +
      "\n" +
      input +
      "\n" +
      "-------------------------------------------------------------" +
      "\n " +
      " OUTPUT: " +
      "\n" +
      result.getOrElse("some default") + "\n\n")
    case AllWorkCompleted =>
      outfile.close()
//      context.system.shutdown()
      isWorkCompleted = true
    case IsWorkCompleted => sender ! isWorkCompleted
  }
}
