package controllers
import com.fasterxml.jackson.annotation.JsonValue
import play.api.{db, _}
import play.api.mvc._
import services.Counter
import play.api.libs.json._
import entity.scala.{Player, User, Users}
import slick.driver.H2Driver.api._
import akka._
import akka.actor.FSM.Failure
import akka.actor.Status.Success
import akka.actor.ActorSystem
import javax.inject._

import net.liftweb.json._
import play.api._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._
import scala.None
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import repository.UserRepository
import net.liftweb.json.Serialization.write
/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
class FootballController extends Controller{
  val db = Database.forConfig("h2mem1")
  val players = Player.players;
  val users = Users.users;

  val setup = DBIO.seq(
    // Create the tables, including primary and foreign keys
    (players.schema).create,
    (users.schema).create,
    players +=Player(1, "Mesut", "Ozil", 11),
    players +=Player(2, "Alexis", "Sanches", 17),
    players +=Player(3, "Jack", "Wilshere", 10),

      users +=User(1,"sergar16","serhii.hokhkalenko@gmail.com","1111", false)
  )
  val setupFuture = Await.result(db.run(setup), 10.seconds)




  def football = Action.async {
    db.run(players.result).map {
      case players => Ok(Json.toJson(players))
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
