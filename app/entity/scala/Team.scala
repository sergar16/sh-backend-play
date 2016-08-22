package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

/**
  * Created by Serhii.Hokhkalenko on 2016-08-22.
  */
case class Team(id: Long, teamName: String,managerid: Long, leagueId: Long, isDeleted: Boolean = false) extends BaseEntity {
  implicit val TeamFormat = Json.format[Team]
}

object Teams {
  val teams = TableQuery[TeamTable]
  lazy val TeamTable = new TableQuery(tag => new TeamTable(tag))
}

class TeamTable(_tableTag: Tag) extends BaseTable[Team](_tableTag, None, "Team") {
  def * = (id, teamName, managerId, leagueId, isDeleted) <> (Team.tupled, Team.unapply)

  def ? = (Rep.Some(id), Rep.Some(teamName), Rep.Some(managerId), Rep.Some(leagueId), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => Team.tupled((_1.get, _2.get, _3.get, _4.get, _5.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("teamId", O.AutoInc, O.PrimaryKey)
  val teamName: Rep[String] = column[String]("teamName", O.Length(150, varying = true))
  val managerId: Rep[Long] = column[Long]("managerId", O.Length(150, varying = true))
  val leagueId: Rep[Long] = column[Long]("leagueId", O.Length(150, varying = true))
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}
