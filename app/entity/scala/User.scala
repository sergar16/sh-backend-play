package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
case class User(id: Long, profileId: Long, login: String, email: String, password: String, isDeleted: Boolean = false) extends BaseEntity {
  implicit val userReads = Json.reads[User]
  implicit val UserFormat = Json.format[User]
}

object Users {
  val users = TableQuery[UserTable]
  lazy val userTable = new TableQuery(tag => new UserTable(tag))
}


class UserTable(_tableTag: Tag) extends BaseTable[User](_tableTag, None, "User") {
  def * = (id, profileId, login, email, password, isDeleted) <>(User.tupled, User.unapply)

  def ? = (Rep.Some(id), Rep.Some(profileId), Rep.Some(login), Rep.Some(email), Rep.Some(password), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("userId", O.AutoInc, O.PrimaryKey)

  def profileId: Rep[Long] = column[Long]("ProfileId")

  val login: Rep[String] = column[String]("login", O.Length(150, varying = true))
  val email: Rep[String] = column[String]("Email", O.Length(150, varying = true))
  val password: Rep[String] = column[String]("Password", O.Length(150, varying = true))
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))

  //foreign keys
  def profiles = foreignKey("profiles_FK", profileId, Profiles.profiles)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

  def profiles2 = profiles.filter(_.id === profileId) // like join

  //indexes
  def idx = index("user_INDEX", (id), unique = true)

}

