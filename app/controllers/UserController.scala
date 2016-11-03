package controllers


import slick.driver.H2Driver.api._

import javax.inject._



import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import net.liftweb.json.Serialization.write
import akka.actor.ActorSystem
import entity.scala.{User, Users}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization._
import play.api.mvc._
import repository.UserRepository
import services.AuthService

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Serhii on 8/23/2016.
  */

  @Singleton
  class UserController @Inject()(actorSystem: ActorSystem, authService: AuthService)(implicit exec: ExecutionContext) extends GenericEntityController[User](actorSystem: ActorSystem, authService: AuthService){

  val db = Database.forConfig("h2mem1")
  val users = Users.users;


  val setup = DBIO.seq(
    // Create the tables, including primary and foreign keys
    (users.schema).create,
    users+= User(1,"sergar16", "serhii.hokhkalenko@gmail.com","1111")
  )
  val setupFuture = Await.result(db.run(setup), 10.seconds)




  implicit val formats = DefaultFormats

  def user = Action.async{
    new UserRepository().getById(1).map {
      case user => Ok(write(user.get))
    }
  }



}
