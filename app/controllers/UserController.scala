package controllers


import javax.inject._

import akka.actor.ActorSystem
import entity.scala.User
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.cache.Cached
import play.api.mvc._
import repository.UserRepository
import services.UserService

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Serhii on 8/23/2016.
  */

@Singleton
class UserController @Inject()(actorSystem: ActorSystem, cached: Cached, userService: UserService)(implicit exec: ExecutionContext) extends Controller {

  //todo: remove it
  UserRepository.initDb()

  implicit val formats = DefaultFormats

  def user = Action.async {
    userService.getById(1).map {
      case user => Ok(write(user.get))
    }
  }

  /**
    * This method returns user by id
    * Method supports caching
    *
    * @param id
    * @return -user
    */
  def userById(id: Int) = cached("user" + id) {
    Action.async {
      userService.getById(id).map {
        case user => Ok(write(user.get))
      }
    }
  }

  def deleteUser(id: Int) = {
    userService.deleteUser(id)
  }

  //  def userProfile = Authenticated {
  //    user =>
  //      cached(req => "profile." + user) {
  //        Action {
  //          Ok(views.html.profile(User.find(user)))
  //        }
  //      }
  //  }


}
