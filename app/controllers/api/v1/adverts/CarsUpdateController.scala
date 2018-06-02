package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import services.CarAdvertsService
import utils.Validators

case class UpdateData(title: Option[String], fuel: Option[String], price: Option[Int], mileage: Option[Int], firstReg: Option[String])

@Singleton
class CarsUpdateController @Inject()(cc: ControllerComponents, service: CarAdvertsService) extends ApiBaseController(cc) {

  val validationForm = Form(
    mapping(
      "title" -> optional(nonEmptyText(maxLength = 128)),
      "fuel" -> optional(Validators.fuel),
      "price" -> optional(number(min = 0)),
      "mileage" -> optional(number(min = 0)),
      "firstReg" -> optional(Validators.regDate)
    )(UpdateData.apply)(UpdateData.unapply)
  )

  def update(id: Int) = safeActionWithValidation(validationForm, (data: UpdateData) => {
    val updated = service.update(id, data)
    if (updated > 0)
      Ok(Json.obj("id" -> id))
    else
      NotFound
  })

}
