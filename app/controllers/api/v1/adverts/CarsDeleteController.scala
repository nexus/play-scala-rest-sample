package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import services.CarAdvertsService

@Singleton
class CarsDeleteController @Inject()(cc: ControllerComponents, service: CarAdvertsService) extends ApiBaseController(cc) {

  def delete(id: Int) = safeAction { implicit request =>
    val isDeleted = service.delete(id)

    if (isDeleted)
      Ok(Json.obj("id" -> id))
    else
      NotFound
  }
}
