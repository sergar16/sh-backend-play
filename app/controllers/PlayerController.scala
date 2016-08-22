package controllers

import entity.scala._
import slick.driver.H2Driver.api._
import net.liftweb.json._
import play.api._
import javax.inject._

import akka.actor.ActorSystem
import play.api.mvc._

import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import repository.{PlayerRepository, UserRepository}
import net.liftweb.json.Serialization.write

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
class PlayerController extends Controller{
  val db = Database.forConfig("h2mem1")
  val users = Users.users;
  val profiles=Profiles.profiles
  val profilesTeams=ProfileTeams.profilesTeams
  val teams = Teams.teams;
  val players =Players.players;

  val setup = DBIO.seq(
    // Create the tables, including primary and foreign keys
   (users.schema++profiles.schema++profilesTeams.schema++teams.schema++players.schema).create,
    users+= User(1,"sergar16", "serhii.hokhkalenko@gmail.com","1111"),

    profiles+=Profile(1, 1,"sergar16", false),

    profilesTeams+=ProfileTeam(1,1),

    teams+=Team(1,"Arsenal Serhii", 1,1),

    players+=Player(1,1,"Mesut", "Ozil", 11),
    players+=Player(2,1,"Alexis", "Sanches", 7),
    players+=Player(2,1,"Laurent", "Koscielny", 6)


  )
  val setupFuture = Await.result(db.run(setup), 10.seconds)




  def football = Action.async {
    val playerRepository= new PlayerRepository
    playerRepository.getPlayersByTeamId(1).map {
      case players => Ok(write(players))
    }
  }


  implicit val formats = DefaultFormats

  def user = Action.async{
    new UserRepository().getById(1).map {
      case user => Ok(write(user.get))
    }
  }



//  def compute(id: String) = Action.async { request =>
//    val result: Future[SomeType] = compute(id)
//    result.map(value => Ok(transform(value, id)))
//      .recover { ex =>
//        Logger.error("Something went wrong", ex)
//        InternalServerError
//      }
//  }
//


//
//  class WeatherController @Inject() (ws: WSClient) extends Controller {
//    /**
//      * Get local weather
//      * @return
//      */
//    def local = Action.async {
//      async {
//        val loc = await(getLocation)
//        val r = await(getWeather(loc.json))
//        Ok(r.json)
//      }
//    }
//
//    def getWeather(loc: JsValue) = {
//      val lat = (loc \ "latitude").as[Double]
//      val lon = (loc \ "longitude").as[Double]
//      ws.url(s"http://api.openweathermap.org/data/2.5/weather?lat=$lat&amp;amp;lon=$lon").get
//    }
//
//    def getLocation = {
//      ws.url("http://www.telize.com/geoip").get
//    }
//  }

}
