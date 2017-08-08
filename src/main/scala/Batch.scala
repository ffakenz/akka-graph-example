class Batch(val matrix : Matrix) {

  def floodFillIteration: Matrix = {

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

}


