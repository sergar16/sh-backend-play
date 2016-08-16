package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
case class Player(id: Long,name:String, Surname:String, number: Long ) {
}

object Player{
  implicit val  playerFormat = Json.format[Player]
  val players = TableQuery[Players]
}


// Definition of the SUPPLIERS table
class Players(tag: Tag) extends Table[(Long, String, String, Long)](tag, "PLAYERS") {
  def id = column[Long]("Player_ID", O.PrimaryKey) // This is the primary key column
  def name = column[String]("Player_NAME")
  def street = column[String]("Player_Surname")
  def number = column[Long]("Player_Surname")
  def * = (id, name, street,number)
}
