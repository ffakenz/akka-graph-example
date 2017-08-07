class Batch(inbatch: Seq[AbstractInput]) {
  val batch = inbatch

  def degree(u: Int): LongResult = LongResult(u)

}
