package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class CarAdvertsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def list = Action {
    NotImplemented(Json.obj("error" -> "Not Implemented"))
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
