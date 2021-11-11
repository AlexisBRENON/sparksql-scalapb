package scalapb.spark

import scalapb.descriptors.FieldDescriptor

trait ColumnNaming {
  private[scalapb] def fieldName(field: FieldDescriptor): String
}

trait ProtoColumnNaming extends ColumnNaming {
  self: ProtoSQL =>
  override private[scalapb] def fieldName(field: FieldDescriptor) = field.name
}

trait ScalaColumnNaming extends ColumnNaming {
  override private[scalapb] def fieldName(field: FieldDescriptor) = field.scalaName
}
