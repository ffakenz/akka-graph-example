sealed trait Position { def z: Int; def y: Int; def x: Int }
case object North extends Position {
  override def z: Int = 0;
  override def y: Int = 1;
  override def x: Int = 0;
}
case object South extends Position {
  override def z: Int = 0;
  override def y: Int = -1;
  override def x: Int = 0;
}
case object East extends Position {
  override def z: Int = 0;
  override def y: Int = 0;
  override def x: Int = -1;
}
case object West extends Position {
  override def z: Int = -1;
  override def y: Int = 0;
  override def x: Int = 0;
}
case object Up extends Position {
  override def z: Int = 1;
  override def y: Int = 0;
  override def x: Int = 0;
}
case object Down extends Position {
  override def z: Int = -1;
  override def y: Int = 0;
  override def x: Int = 0;
}



