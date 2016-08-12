package controllers
import javax.inject._

import com.fasterxml.jackson.annotation.JsonValue
import entity.{Player, PlayerS}
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.Counter

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
class FootballController extends Controller{
  var list=Array(
//    Player.createPlayer("Mesut","Ozil",11),
//    Player.createPlayer("Alexis","Sanches",17),
//    Player.createPlayer("Jackie","Wilshere",10)
    PlayerS("Mesut","Ozil",11),
    PlayerS("Alexis","Sanches",17)
    //    Player.createPlayer("Jackie","Wilshere",10)
  )

  def football =Action{
    Ok(Json.toJson(list))
  }

}
