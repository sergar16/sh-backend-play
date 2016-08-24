package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api.mvc._
import services.AuthService

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Serhii on 8/23/2016.
  */

@Singleton
class GenericEntityController[Entity] @Inject()(actorSystem: ActorSystem, authService: AuthService)(implicit exec: ExecutionContext) extends Controller {
  def generic = Action {
    Ok("work")
  }
}
