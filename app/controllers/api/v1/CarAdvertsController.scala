package controllers.api.v1

import java.time.LocalDate

import controllers.ApiBaseController
import javax.inject._
import models.{CarAdvert, CarAdvertNew, CarAdvertOld, Fuel}
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class CarAdvertsController @Inject()(cc: ControllerComponents) extends ApiBaseController(cc) {

  def list = safeAction { implicit request =>
    val newAd = CarAdvertNew(Some(1), "A4 avavnt", Fuel.Gasoline, 10000)

    val oldCarReg = LocalDate.parse("2017-01-01")
    val oldAd = CarAdvertOld(Some(2), "A6 avavnt", Fuel.Gasoline, 10000, 5320, oldCarReg)

    val ads = List[CarAdvert](newAd, oldAd)

    Ok(
      Json.toJson(ads)
    )
  }

  def get(id: Long) = Action {
    NotImplemented(Json.obj("error" -> "Not Implemented"))
  }

  def create() = Action {
    NotImplemented(Json.obj("error" -> "Not Implemented"))
  }

  def update(id: Long) = Action {
    NotImplemented(Json.obj("error" -> "Not Implemented"))
  }

  def delete(id: Long) = Action {
    NotImplemented(Json.obj("error" -> "Not Implemented"))
  }

}
