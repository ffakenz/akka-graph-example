import akka.actor.{Props, ActorSystem}
import akka.kernel.Bootable

class Microkernel extends Bootable {
  val system = ActorSystem("Microkernel")
//  val batch = ParseConfig.readBatch()
//  val masterLocation = ParseConfig.getMasterLocation()

  def startup = {
//    system.actorOf(Props(new BatchWorker(batch, masterLocation)), "worker")
  }

  def shutdown = {
    system.shutdown()
  }
}
