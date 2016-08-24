package controllers

import javax.inject._

import akka.actor.ActorSystem
import entity.scala.User
import play.api.mvc._
import services.AuthService

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Serhii on 8/23/2016.
  */

  @Singleton
  class UserController @Inject()(actorSystem: ActorSystem, authService: AuthService)(implicit exec: ExecutionContext) extends GenericEntityController[User](actorSystem: ActorSystem, authService: AuthService){

    def auth(login: String, password: String) = Action.async {
      implicit request => {
        authService.auth(login, password).map {
          case true => Ok("succcesss")
          case false => Forbidden("failed")
        }
      }

    }





}
