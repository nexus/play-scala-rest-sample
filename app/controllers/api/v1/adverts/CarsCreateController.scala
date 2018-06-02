package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import services.CarAdvertsService
import utils.Validators

case class CreateData(title: String, fuel: String, price: Int, isNew: Boolean, mileage: Option[Int], firstReg: Option[String])

@Singleton
class CarsCreateController @Inject()(cc: ControllerComponents, service: CarAdvertsService) extends ApiBaseController(cc) {

  val validationForm = Form(
    mapping(
      "title" -> nonEmptyText(maxLength = 128),
      "fuel" -> Validators.fuel,
      "price" -> number(min = 0),
      "isNew" -> boolean,
      "mileage" -> optional(number(min = 0)),
      "firstReg" -> optional(Validators.regDate)
    )(CreateData.apply)(CreateData.unapply)
  )

  def create = safeActionWithValidation(validationForm, (data: CreateData) => {
    val created = service.create(data)

    Created(Json.toJson(created))
  })
}
