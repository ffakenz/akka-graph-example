package abstraccions

sealed trait Coordinate { def z: Int; def y: Int; def x: Int;}
case class Air(z: Int, y: Int, x: Int) extends Coordinate {
  private val value = 0
  override def toString = s"(${z}, ${y}, ${x}) = ${value}"
}
case class Water(z: Int, y: Int, x: Int) extends Coordinate {
  private val value = 1
  override def toString = s"(${z}, ${y}, ${x}) = ${value}"
}
case class Dirt(z: Int, y: Int, x: Int) extends Coordinate {
  private val value = 2
  override def toString = s"(${z}, ${y}, ${x}) = ${value}"
}


