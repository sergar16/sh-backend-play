package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._


/**
  * Created by serhii.hokhkalenko on 2016-11-07.
  */
case class UsersFriends(id: Long, userId: Long, userFriendId: Long, isDeleted: Boolean = false) extends BaseEntity {
  implicit val usersFriendsReads = Json.reads[UsersFriends]
  implicit val usersFriendsFormat = Json.format[UsersFriends]
}

object UsersFriendss {
  val usersFriendss = TableQuery[UsersFriendsTable]
  lazy val usersFriendsTable = new TableQuery(tag => new UsersFriendsTable(tag))
}


class UsersFriendsTable(_tableTag: Tag) extends BaseTable[UsersFriends](_tableTag, None, "UsersFriends") {
  def * = (id, userId, userFriendId, isDeleted) <>(UsersFriends.tupled, UsersFriends.unapply)

  def ? = (Rep.Some(id), Rep.Some(userId), Rep.Some(userFriendId), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => UsersFriends.tupled((_1.get, _2.get, _3.get, _4.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("usersFriendsId", O.AutoInc, O.PrimaryKey)

  def profileId: Rep[Long] = column[Long]("ProfileId")

  val userId: Rep[Long] = column[Long]("userId", O.Length(150, varying = true))
  val userFriendId: Rep[Long] = column[Long]("userFriendId", O.Length(150, varying = true))
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))

  //indexes
  def idx = index("UsersFriends_INDEX", (id), unique = true)

  def idxU2U = index("UsersFriends_INDEX_USER_TO_USER", (userId, userFriendId), unique = true)

}

