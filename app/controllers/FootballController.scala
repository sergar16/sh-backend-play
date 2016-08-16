package controllers
import javax.inject._

import com.fasterxml.jackson.annotation.JsonValue
import entity.scala.Player
import play.api._
import play.api.mvc._
import services.Counter
import play.api.libs.json._
import entity.scala.Player

import slick.driver.H2Driver.api._
import play.api.Logger

import scala.concurrent.ExecutionContext.Implicits.global
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

var playersdb=null;


      try {
        val players = Player.players;
        val db = Database.forConfig("h2mem1")
        val setup = DBIO.seq(
          // Create the tables, including primary and foreign keys

          (players.schema).create,

          // Insert some suppliers
          players +=(1, "Mesut", "Ozil", 11),
          players +=(2, "Alexis", "Sanches", 17),
          players +=(3, "Jack", "Wilshere", 10)
          // Equivalent SQL code:
          // insert into SUPPLIERS(SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP) values (?,?,?,?,?,?)
          // Equivalent SQL code:
          // insert into COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL) values (?,?,?,?,?)
        )

        val setupFuture = db.run(setup)

      val r= db.run(players.result)
      }
     // finally db.close



    val players= Json.toJson( list)
    Ok(players)
  }

}
