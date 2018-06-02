package controllers

import javax.inject.Inject

import play.api.data._
import play.api.libs.json.Json
import play.api.mvc._

class ApiBaseController @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {

  def safeActionWithValidation[F](form: Form[F], process: F => Result) = safeAction { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => {
        val messages = formWithErrors.errors.map(err => s"${err.key}: ${err.message }")
        BadRequest(
          Json.obj(
            "error" -> "validation error",
            "fields" -> Json.toJson(messages)
          )
        )
      },
      value => process(value)
    )
  }

  def safeAction(doSafe: Request[AnyContent] => Result) = Action { implicit request =>
    try {
      doSafe(request)
    } catch {
      case e: Exception =>
        InternalServerError(Json.obj("error" -> e.getMessage())) //TODO response builder
    }
  }

}
