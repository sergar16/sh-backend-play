package controllers

import services.AuthService
import javax.inject._

import akka.actor.ActorSystem
import play.api.mvc._

import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import net.liftweb.json.Serialization.write

/**
  * Created by Serhii on 8/23/2016.
  */

  @Singleton
  class AuthController @Inject()(actorSystem: ActorSystem,authService: AuthService)(implicit exec: ExecutionContext) extends Controller {

    def auth(login: String, password: String) = Action.async {
      implicit request => {
        authService.auth(login, password).map {
          case true => Ok("succcesss")
          case false => Forbidden("failed")
        }
      }
    }





}
