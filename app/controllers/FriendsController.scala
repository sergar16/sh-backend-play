package controllers

import javax.inject._

import akka.actor.ActorSystem
import entity.scala.{User, UsersFriends}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.cache.Cached
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import repository.{UserRepository, UsersFriendsRepository}
import services.UserService

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Serhii on 8/23/2016.
  */

@Singleton
class FriendsController @Inject()(actorSystem: ActorSystem, cached: Cached, usersFriendsRepository: UsersFriendsRepository)(implicit exec: ExecutionContext) extends Controller {

  implicit val formats = DefaultFormats
  implicit val userFriendsReads = Json.reads[UsersFriends]

  def getUserFriendsList(userId: Long)= Action.async {
    usersFriendsRepository.getUserFriendsList(userId).map {
      case friends => Ok(write(friends))
    }
  }


}
