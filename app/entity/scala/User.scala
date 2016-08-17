package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
case class User(id: Long, userName: String, email: String, password: String, isDeleted: Boolean = false) extends BaseEntity {
  implicit val UserFormat = Json.format[User]
}

object Users {
  val users = TableQuery[UserTable]
  lazy val userTable = new TableQuery(tag => new UserTable(tag))
}


// Definition of the SUPPLIERS table
class UserTable(_tableTag: Tag) extends BaseTable[User](_tableTag, None, "User") {
  def * = (id, userName, email, password, isDeleted) <> (User.tupled, User.unapply)

  def ? = (Rep.Some(id), Rep.Some(userName), Rep.Some(email), Rep.Some(password), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2.get, _3.get, _4.get, _5.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("UserId", O.AutoInc, O.PrimaryKey)
  val userName: Rep[String] = column[String]("UserName", O.Length(150, varying = true))
  val email: Rep[String] = column[String]("Email", O.Length(150, varying = true))
  val password: Rep[String] = column[String]("Password", O.Length(150, varying = true))
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}

