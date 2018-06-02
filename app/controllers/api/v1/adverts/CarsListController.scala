package controllers.api.v1.adverts

import java.time.LocalDate

import controllers.ApiBaseController
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import models.{CarAdvert, CarAdvertNew, CarAdvertOld, Fuel}

case class ListData(sortBy: Option[String])

@Singleton
class CarsListController @Inject()(cc: ControllerComponents) extends ApiBaseController(cc) {

  val validationForm = Form(
    mapping(
      "sortBy" -> optional(nonEmptyText)
    )(ListData.apply)(ListData.unapply)
  )

  def list = safeActionWithValidation(validationForm, (data: ListData) => {
      val newAd = CarAdvertNew(Some(1), "AUDI A4 avant", Fuel.Gasoline, 10000)

      val oldCarReg = LocalDate.parse("2017-01-01")
      val oldAd = CarAdvertOld(Some(2), "AUDI A6 avant", Fuel.Gasoline, 10000, 5320, oldCarReg)

      val ads = List[CarAdvert](newAd, oldAd)

      Ok(Json.toJson(ads))
    }
  )

}
