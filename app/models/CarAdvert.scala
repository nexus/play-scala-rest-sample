package models

import java.time.LocalDate

import play.api.libs.json._

abstract class CarAdvert {
  val id: Option[Int]
  val title: String
  val fuel: Fuel.Value
  val price: Int
  val isNew: Boolean
}

case class CarAdvertNew(id: Option[Int], title: String, fuel: Fuel.Value, price: Int) extends CarAdvert {
  val isNew = true
}

case class CarAdvertOld(id: Option[Int], title: String, fuel: Fuel.Value, price: Int, mileage: Int, firstReg: LocalDate) extends CarAdvert {
  val isNew = false
}

object CarAdvertNew {
  implicit val writer = new Writes[CarAdvertNew] {
    def writes(c: CarAdvertNew): JsValue = {
      Json.obj(
        "id" -> c.id,
        "title" -> c.title,
        "fuel" -> c.fuel,
        "price" -> c.price,
        "isNew" -> c.isNew
      )
    }
  }
}

object CarAdvertOld {
  implicit val writer = new Writes[CarAdvertOld] {
    def writes(c: CarAdvertOld): JsValue = {
      Json.obj(
        "id" -> c.id,
        "title" -> c.title,
        "fuel" -> c.fuel,
        "price" -> c.price,
        "isNew" -> c.isNew,
        "mileage" -> c.mileage,
        "firstReg" -> c.firstReg
      )
    }
  }
}

object CarAdvert {
  implicit val writer = new Writes[CarAdvert] {
    def writes(c: CarAdvert): JsValue =
      c match {
        case x: CarAdvertNew => Json.toJson(x)
        case y: CarAdvertOld => Json.toJson(y)
      }
  }
}
