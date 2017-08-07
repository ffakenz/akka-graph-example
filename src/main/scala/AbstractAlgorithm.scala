trait AbstractInput
trait AbstractResult

trait AbstractAlgorithm {
  def execute(batch: Batch, input: AbstractInput): Option[AbstractResult]
}

case class Coordinate(x: Int, y: Int, z: Int) extends AbstractInput {
  override def toString = x.toString + "," + y.toString + "," + z.toString
}

case class LongResult(result: Int) extends AbstractResult {
  override def toString = result.toString
}

object DegreeAlgorithm extends AbstractAlgorithm {
  override def execute(batch: Batch, input: AbstractInput): Option[AbstractResult] = input match {
    case Coordinate(x,y,z) => Some(batch.degree(x))
    case _ => None
  }
}
