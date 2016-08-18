package controllers
import com.fasterxml.jackson.annotation.JsonValue
import play.api.{db, _}
import play.api.mvc._
import services.Counter
import play.api.libs.json._
import entity.scala.{Player, Players, User, Users}
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
import repository.{PlayerRepository, UserRepository}
import net.liftweb.json.Serialization.write
/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
class FootballController extends Controller{
  val db = Database.forConfig("h2mem1")
  val players =Players.players;
  val users = Users.users;

  val setup = DBIO.seq(
    // Create the tables, including primary and foreign keys
    (players.schema).create,
    (users.schema).create,
    players +=Player(1,1, "Mesut", "Ozil", 11,false),
    players +=Player(2, 1,"Alexis", "Sanches", 17,false),
    players +=Player(3, 2,"Jack", "Wilshere", 10,false),

      users +=User(1,"pandafx","pandafx@gmail.com","1111", false),
      users +=User(2,"sergar16","serhii.hokhkalenko@gmail.com","1111", false)
  )
  val setupFuture = Await.result(db.run(setup), 10.seconds)




  def football = Action.async {
    val playerRepository= new PlayerRepository
    playerRepository.getPlayersByUserId(1).map {
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
