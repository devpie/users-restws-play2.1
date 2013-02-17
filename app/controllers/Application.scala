package controllers

import play.api._
import play.api.mvc._
import models._
import org.codehaus.jackson.JsonNode
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.xml._

object Application extends Controller {
  implicit val readsUser = Json.reads[JsonUser]
  implicit val writesUser = Json.writes[User]

  def index = Action {
    Ok(views.html.index("Play 2.1 REST WS example"))
  }

  def list = Action {
    Ok(Json.toJson(Users.findAll))
  }

  def createUser = Action(parse.json) { request =>
    Json.fromJson[JsonUser](request.body) match {
      case JsSuccess(u, _) => Users.create(u) map { cu => Ok(Json.toJson(cu)) } getOrElse { Status(500) }
      case _ => Status(500)
    }
  }

  def deleteUser(id: Long) = Action {
    Users.delete(id) match {
      case 0 => Status(404)
      case _ => Ok
    }
  }

  def user(id: Long) = Action {
    Users.find(id) map {
      u => Ok(Json.toJson(u))
    } getOrElse {
      Status(404)
    }
  }

  def updateUser(id: Long) = Action(parse.json) { request =>
    Json.fromJson[JsonUser](request.body) match {
      case JsSuccess(u, _) => Users.update(id, u) map { cu => Ok(Json.toJson(cu)) } getOrElse { Status(500) }
      case JsError(e) => Status(500)(e.toString())
    }
  }

}