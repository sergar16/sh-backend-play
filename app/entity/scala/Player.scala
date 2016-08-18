package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */



case class Player(id: Long,userId: Long, name: String, surname: String, number: Long, isDeleted: Boolean = false) extends BaseEntity {
  implicit val UserFormat = Json.format[User]
}

object Players {
  val players = TableQuery[PlayerTable]
  lazy val playersTable = new TableQuery(tag => new PlayerTable(tag))
}


// Definition of the SUPPLIERS table
class PlayerTable(_tableTag: Tag) extends BaseTable[Player](_tableTag, None, "PLAYERS") {
  def * = (id,userId, name, surname, number, isDeleted) <> (Player.tupled, Player.unapply)

  def ? = ( Rep.Some(id),Rep.Some(userId),Rep.Some(name), Rep.Some(surname), Rep.Some(number), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => Player.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
  val userId: Rep[Long] = column[Long]("user_id")
  val name: Rep[String] = column[String]("name", O.Length(150, varying = true))
  val surname: Rep[String] = column[String]("surname", O.Length(150, varying = true))
  val number: Rep[Long] = column[Long]("number")
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}