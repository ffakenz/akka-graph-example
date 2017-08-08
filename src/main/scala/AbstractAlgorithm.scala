import abstraccions._

trait AbstractInput
trait AbstractOutput
trait AbstractAlgorithm {
  def execute(input: AbstractInput): Option[AbstractOutput]
}


case object FloodFill extends AbstractAlgorithm {

  def floodFillIteration(matrix: Matrix): Matrix = {

    val newMatrix = Matrix(matrix.coordinates.clone)

    newMatrix.coordinates
      .filter { case w: Water => true } // water elements
      .flatMap { newMatrix.getAdjacentList(_) } // list of adjacents
      .distinct
      .filter { case a: Air => true } // list of air adjacents
      .foreach( (c: Coordinate) => { // side effect
      val indx = newMatrix.coordinates.indexOf(c)
      val water = Water(c.z, c.y, c.x)
      newMatrix.coordinates.update(indx, water)
    })

    newMatrix
  }

  override def execute(input: AbstractInput): Option[AbstractOutput] = input match {
    case i@ Matrix(_) => Some( floodFillIteration(i) )
    case _ => None
  }
}



case class Matrix(coordinates: Array[Coordinate]) extends AbstractInput with AbstractOutput {

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


