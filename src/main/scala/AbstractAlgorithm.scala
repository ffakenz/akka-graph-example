trait AbstractInput
trait AbstractResult

trait AbstractAlgorithm {
  def execute(batch: Batch, input: AbstractInput): Option[AbstractResult]
}

case class Coordinate(x: Int, y: Int, z: Int) extends AbstractInput {
  override def toString = x.toString + "," + y.toString + "," + z.toString
}


case class Chunk(val matrix: Array[Array[Array[Int]]]) extends AbstractInput {
  override def toString = "chunk"

}

case class MatrixResult(result: Array[Array[Array[Int]]]) extends AbstractResult {
  //override def toString = " z = 0:\n" + result(0).map(_.mkString(" ")).mkString("\n")
  def printOneZLayer(z: Int): String =  " z = "+ z +":\n" + result(z).map(_.mkString(" ")).mkString("\n")
  override def toString = result.indices.map(z => printOneZLayer(z)).mkString("\n") 
}

object DegreeAlgorithm extends AbstractAlgorithm {
  override def execute(batch: Batch, input: AbstractInput): Option[AbstractResult] = input match {
    //case Coordinate(x,y,z) => Some(batch.degree(x))
    case Chunk(matrix) => Some(batch.floodFillIteration( matrix ))
    case _ => None
  }
}
