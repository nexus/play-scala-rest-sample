package models

import play.api.libs.json.{Reads, Writes}

object Fuel extends Enumeration {
  type Fuel = Value
  val gasoline = Value("gasoline")
  val diesel = Value("diesel")

  def withNameOpt(s: String): Option[Value] = values.find(_.toString == s)

  implicit val myEnumReads = Reads.enumNameReads(Fuel)
  implicit val myEnumWrites = Writes.enumNameWrites

}

