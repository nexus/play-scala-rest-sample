package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import services.CarAdvertsService

@Singleton
class CarsGetController @Inject()(cc: ControllerComponents, service: CarAdvertsService) extends ApiBaseController(cc) {

  def get(id: Int) = safeAction { implicit request =>
    service.get(id) match {
      case None => NotFound
      case Some(x) => Ok(Json.toJson(x))
    }
  }
}
