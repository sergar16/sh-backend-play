package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
//todo: add fields to prodile
case class Profile(id: Long, isDeleted: Boolean = false) extends BaseEntity {
  implicit val ProfileFormat = Json.format[Profile]
}

object Profiles {
  val Profiles = TableQuery[ProfileTable]
  lazy val ProfileTable = new TableQuery(tag => new ProfileTable(tag))
}


class ProfileTable(_tableTag: Tag) extends BaseTable[Profile](_tableTag, None, "Profile") {
  def * = (id, isDeleted) <>(Profile.tupled, Profile.unapply)

  def ? = (Rep.Some(id), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => Profile.tupled((_1.get, _2.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("ProfileId", O.AutoInc, O.PrimaryKey)
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}

