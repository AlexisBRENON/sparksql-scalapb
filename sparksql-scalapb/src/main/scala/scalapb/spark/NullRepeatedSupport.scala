package scalapb.spark

trait NullRepeatedSupport {
  private[scalapb] val supportNullRepeated: Boolean
}

trait NoNullRepeatedSupport extends NullRepeatedSupport {
  self: ProtoSQL =>

  private[scalapb] val supportNullRepeated = false
}

trait EnabledNullRepeatedSupport extends NullRepeatedSupport {
  self: ProtoSQL =>

  private[scalapb] val supportNullRepeated = true
}
