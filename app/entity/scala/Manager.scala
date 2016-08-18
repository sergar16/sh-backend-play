package entity.scala

import play.api.libs.json.Json

/**
  * Created by serhii.hokhkalenko on 2016-08-16.
  */
case class Manager(name: String)

object Managers{
  implicit val  playerFormat = Json.format[Manager]
}
