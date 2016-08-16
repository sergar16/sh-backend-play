package entity.scala

import play.api.libs.json.Json

/**
  * Created by serhii.hokhkalenko on 2016-08-16.
  */
case class Club(manager: Manager,team: List[Player])

object Club{
  implicit val  playerFormat = Json.format[Club]
}
