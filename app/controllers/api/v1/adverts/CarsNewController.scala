package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

case class NewData(title: Option[String], fuel: Option[String], price: Option[Int], isNew: Option[Boolean], mileage: Option[Int], firstReg: Option[String])

object NewData {
  implicit val writer = Json.writes[NewData]
}

@Singleton
class CarsNewController @Inject()(cc: ControllerComponents) extends ApiBaseController(cc) {

  val validationForm = Form(
    mapping(
      "title" -> optional(nonEmptyText),
      "fuel" -> optional(nonEmptyText),
      "price" -> optional(number),
      "isNew" -> optional(boolean),
      "mileage" -> optional(number),
      "firstReg" -> optional(nonEmptyText)
    )(NewData.apply)(NewData.unapply)
  )

  def create = safeActionWithValidation(validationForm, (data: NewData) => {
      NotImplemented(Json.toJson(data))
    }
  )

}
