package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import services.CarAdvertsService

case class CreateData(title: String, fuel: String, price: Int, isNew: Boolean, mileage: Option[Int], firstReg: Option[String])

object CreateData {
  implicit val writer = Json.writes[CreateData]
}

@Singleton
class CarsCreateController @Inject()(cc: ControllerComponents, service: CarAdvertsService) extends ApiBaseController(cc) {

  val validationForm = Form(
    mapping(
      "title" -> nonEmptyText,
      "fuel" -> nonEmptyText,
      "price" -> number,
      "isNew" -> boolean,
      "mileage" -> optional(number),
      "firstReg" -> optional(nonEmptyText)
    )(CreateData.apply)(CreateData.unapply)
  )

  def create = safeActionWithValidation(validationForm, (data: CreateData) => {
    val created = service.create(data)

    Created(Json.toJson(created))
  })
}
