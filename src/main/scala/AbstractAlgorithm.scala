trait AbstractInput
trait AbstractResult

trait AbstractAlgorithm {
  def execute(batch: Batch, input: AbstractInput): Option[AbstractResult]
}


object DegreeAlgorithm extends AbstractAlgorithm {
  override def execute(batch: Batch, input: AbstractInput): Option[AbstractResult] = input match {
    case m @ Matrix(_) => Some( batch.floodFillIteration(m) )
    case _ => None
  }
}

case class Coordinate(z: Int, y: Int, x: Int, value: Int = 0 ) {
  override def toString = s"(${z}, ${y}, ${x}) = ${value}"
}

case class Matrix(coordinates: Array[Coordinate]) extends AbstractInput with AbstractResult {

  def Neighbor(p: Position, c: Coordinate): Option[Coordinate] =
    coordinates find { _ == Coordinate(c.z + p.z, c.y + p.y, c.x + p.x) }

  def getAdjacentList(c: Coordinate): Array[Coordinate] =
    Array(
      Neighbor(East, c)
      , Neighbor(North, c)
      , Neighbor(West, c)
      , Neighbor(South, c)
      , Neighbor(Down, c)
      , Neighbor(Up, c)
    ) flatten // clean of None values


  override def toString = coordinates.groupBy(_.z).values.map((cs: Array[Coordinate]) => {
    cs.map(_.toString).mkString("\n")
  }).mkString("\n")
}


