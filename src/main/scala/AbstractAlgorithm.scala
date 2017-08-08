trait AbstractInput
trait AbstractResult

trait AbstractAlgorithm {
  def execute(batch: Batch, input: AbstractInput): Option[AbstractResult]
}


object FloodFill extends AbstractAlgorithm {
  override def execute(batch: Batch, input: AbstractInput): Option[AbstractResult] = input match {
    case Matrix(_) => Some( batch.floodFillIteration )
    case _ => None
  }
}



case class Matrix(coordinates: Array[Coordinate]) extends AbstractInput with AbstractResult {

  def Neighbor(p: Position, c: Coordinate): Option[Coordinate] =
    coordinates find {
      case coordinate: Coordinate
          if(coordinate.z == c.z + p.z &&
            coordinate.y == c.y + p.y &&
            coordinate.x == c.x + p.x) => true
    }

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


