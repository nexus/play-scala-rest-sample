package controllers.api.v1.adverts

import controllers.ApiBaseController
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class CarsDeleteController @Inject()(cc: ControllerComponents) extends ApiBaseController(cc) {

  def delete(id: Long) = safeAction { implicit request =>
    NotImplemented(Json.obj("error" -> "Not Implemented"))
  }

}
