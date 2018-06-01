package models

import play.api.libs.json.{Reads, Writes}

object Fuel extends Enumeration {
  type Fuel = Value
  val Gasoline, Diesel = Value

  implicit val myEnumReads = Reads.enumNameReads(Fuel)
  implicit val myEnumWrites = Writes.enumNameWrites
}