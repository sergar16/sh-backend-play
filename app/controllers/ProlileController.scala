package controllers


import javax.inject._

import akka.actor.ActorSystem
import entity.scala.{Profile, User}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.cache.Cached
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import services.ProfileService

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Serhii on 8/23/2016.
  */

@Singleton
class ProfileController @Inject()(actorSystem: ActorSystem, cached: Cached, profileService: ProfileService)(implicit exec: ExecutionContext) extends Controller {

  //todo: remove it
  //ProfileRepository.initDb()

  implicit val formats = DefaultFormats
  implicit val ProfileReads = Json.reads[Profile]

  /**
    * This function returns Profile by id
    * function supports caching
    *
    * @param id
    * @return -Profile
    */
  def profileById(id: Long) = cached("Profile" + id) {
    Action.async {
      profileService.getById(id).map {
        case Some(u) => Ok(write(u))
        case None => Forbidden(Json.obj("status" -> "KO", "message" -> ("Profile with id= '" + id + "' is not exist.")))
      }
    }
  }

  /**
    * This function adding new Profile
    *
    * @return
    */
  def addProfile = Action(parse.json) {
    request =>
      val ProfileResult = request.body.validate[Profile]
      ProfileResult.fold(
        errors => {
          BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
        },
        Profile => {
          profileService.addProfile(Profile)
          Ok(Json.obj("status" ->"OK", "message" -> ("Profile '"+Profile.id+"' saved.") ))
        }
      )
  }

  /**
    * This function updating profile
    *
    * @return
    */
  def updateProfile() = Action(parse.json){
    request =>
      val ProfileResult = request.body.validate[Profile]
      ProfileResult.fold(
        errors => {
          BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
        },
        Profile => {
          profileService.updateProfile(Profile)
          Ok(Json.obj("status" ->"OK", "message" -> ("Profile '"+Profile.id+"' saved.") ))
        }
      )
  }

  def getAll = Action.async {
    profileService.getAllProfiles.map {
      case profiles => Ok(write(profiles))
    }
  }

  /**
    * This function returns users profile by userId
    *
    * @param userId
    * @return - profile
    */
  def getUsersProfileById(userId:Long)=Action.async {
    profileService.getUserProfileByUserId(userId).map {
      case Some(profile) => Ok(write(profile))
      case None => Forbidden(Json.obj("status" -> "KO", "message" -> ("Profile is not exist.")))
    }
  }


  //  def ProfileProfile = Authenticated {
  //    Profile =>
  //      cached(req => "profile." + Profile) {
  //        Action {
  //          Ok(views.html.profile(Profile.find(Profile)))
  //        }
  //      }
  //  }


}
