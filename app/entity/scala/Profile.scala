package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

/**
  * Created by Serhii.Hokhkalenko on 2016-08-22.
  */
case class Profile(id: Long, profileTeamsId: Long, nickName: String, isDeleted: Boolean = false) extends BaseEntity {
  implicit val ProfileFormat = Json.format[Profile]
}

object Profiles {
  val profiles = TableQuery[ProfileTable]
  lazy val ProfileTable = new TableQuery(tag => new ProfileTable(tag))
}


class ProfileTable(_tableTag: Tag) extends BaseTable[Profile](_tableTag, None, "Profile") {
  def * = (id, profileTeamsId, nickName, isDeleted) <>(Profile.tupled, Profile.unapply)

  def ? = (Rep.Some(id), Rep.Some(profileTeamsId), Rep.Some(nickName), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => Profile.tupled((_1.get, _2.get, _3.get, _4.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("ProfileId", O.AutoInc, O.PrimaryKey)
  val nickName: Rep[String] = column[String]("ProfileName", O.Length(150, varying = true))
  val profileTeamsId: Rep[Long] = column[Long]("profileTeamsId")
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}

/**
  * Profile - > Teams
  */


case class ProfileTeam(id: Long, profileTeamsId: Long, isDeleted: Boolean = false) extends BaseEntity

object ProfileTeams {
  val profilesTeams = TableQuery[ProfileTeamsTable]
  lazy val ProfileTeamsTable = new TableQuery(tag => new ProfileTeamsTable(tag))
}


class ProfileTeamsTable(_tableTag: Tag) extends BaseTable[ProfileTeam](_tableTag, None, "ProfileTeams") {
  def * = (id, profileTeamsId, isDeleted) <>(ProfileTeam.tupled, ProfileTeam.unapply)

  def ? = (Rep.Some(id), Rep.Some(profileTeamsId), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => ProfileTeam.tupled((_1.get, _2.get, _3.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("TeamId", O.AutoInc, O.PrimaryKey)
  val profileTeamsId: Rep[Long] = column[Long]("profileTeamsId")
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}