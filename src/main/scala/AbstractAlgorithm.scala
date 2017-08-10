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
  override def toString = s"${value}"
  //override def toString = s"(${z}, ${y}, ${x}) = ${value}"
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
      , Neighbor(Up, c)
      , Neighbor(Down, c)
    ) flatten // clean of None values



  override def toString = {

    var s  = new String
    var previous_y = 0
    var previous_z = 0
    for (index <- coordinates.indices){
      val coordinate = coordinates(index)
      if (coordinate.y != previous_y) {s+= '\n';previous_y=coordinate.y}
      if (coordinate.z != previous_z) {s+= '\n';previous_z=coordinate.z}
        s += coordinate + " "
    }
    s
  }
}
