package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import services.CarAdvertsService

case class ListData(sortBy: Option[String])

@Singleton
class CarsListController @Inject()(cc: ControllerComponents, service: CarAdvertsService) extends ApiBaseController(cc) {

  val validationForm = Form(
    mapping(
      "sortBy" -> optional(nonEmptyText)
    )(ListData.apply)(ListData.unapply)
  )

  def list = safeActionWithValidation(validationForm, (data: ListData) => {
    val sortBy = data.sortBy.getOrElse("id")
    val ads = service.list(sortBy)

    if (ads.nonEmpty)
      Ok(Json.toJson(ads))
    else
      NotFound
  })
}
