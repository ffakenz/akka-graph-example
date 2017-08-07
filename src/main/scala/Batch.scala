
class Batch(val matrix : Matrix) {


  def floodFillIteration(m : Matrix): Matrix = {

    val newMatrix = Matrix(matrix.coordinates.clone)

    newMatrix.coordinates
          .filter { _.value == 1 } // water elements
          .flatMap { newMatrix.getAdjacentList(_) } // list of adjacents
          .distinct
          .filter { _.value == 0 } // list of air adjacents
          .foreach( (c: Coordinate) => { // side effect
            val indx = newMatrix.coordinates.indexOf(c)
            val newCoordinate = Coordinate(c.z, c.y, c.x, 1)
            newMatrix.coordinates.update(indx, newCoordinate)
          })

    newMatrix
  }

}


