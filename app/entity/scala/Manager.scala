package entity.scala

import play.api.libs.json.Json

/**
  * Created by serhii.hokhkalenko on 2016-08-16.
  */
case class Manager(name: String)

object Manager{
  implicit val  playerFormat = Json.format[Manager]
}
