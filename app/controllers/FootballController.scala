package controllers
import javax.inject._

import com.fasterxml.jackson.annotation.JsonValue
import entity.scala.Player
import play.api.{db, _}
import play.api.mvc._
import services.Counter
import play.api.libs.json._
import entity.scala.Player
import slick.driver.H2Driver.api._
import akka._
import akka.actor.FSM.Failure
import akka.actor.Status.Success
import akka.actor.ActorSystem
import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._
import scala.None
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
class FootballController extends Controller{
  var list=List(
//    Player.createPlayer("Mesut","Ozil",11),
//    Player.createPlayer("Alexis","Sanches",17),
//    Player.createPlayer("Jackie","Wilshere",10)
    Player(1,"Mesut","Ozil",11),
    Player(2,"Alexis","Sanches",17)
    //    Player.createPlayer("Jackie","Wilshere",10)
  )

  def football =Action{
    val players= Json.toJson(list)
    Ok(players)
  }


  def footballfuture =Action.async {
    getFutureMessage(150.second).map { msg => Ok(Json.toJson(msg)) }

  }


  private def getFutureMessage(delayTime: FiniteDuration): Future[Seq[Player]] = {

    val players = Player.players;
    val db = Database.forConfig("h2mem1")
    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys

      (players.schema).create,

      // Insert some suppliers
      players +=Player(1, "Mesut", "Ozil", 11),
      players +=Player(2, "Alexis", "Sanches", 17),
      players +=Player(3, "Jack", "Wilshere", 10)
      // Equivalent SQL code:
      // insert into SUPPLIERS(SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP) values (?,?,?,?,?,?)
      // Equivalent SQL code:
      // insert into COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL) values (?,?,?,?,?)
    )

    val setupFuture =Await.result( db.run(setup),10.seconds)

    //      val result= db.run(players.result)
    //      Await.result(db.run(DBIO.seq(
    //        players.result.map(println)
    //      )), 10 seconds)

  db.run(players.result)
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
