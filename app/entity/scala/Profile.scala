package entity.scala

import play.api.libs.json._
import slick.driver.H2Driver.api._

/**
  * Created by serhii.hokhkalenko on 2016-08-12.
  */
//todo: add fields to prodile
case class Profile(id: Long,name:String,surname:String,phoneExt:String,phone:String,nationality:String,photo:String, isDeleted: Boolean = false) extends BaseEntity {
  implicit val profileReads = Json.reads[Profile]
  implicit val profileFormat = Json.format[Profile]
}

object Profiles {
  val profiles = TableQuery[ProfileTable]
  lazy val ProfileTable = new TableQuery(tag => new ProfileTable(tag))
}


class ProfileTable(_tableTag: Tag) extends BaseTable[Profile](_tableTag, None, "Profile") {
  def * = (id, name, surname, phoneExt, phone, nationality, photo, isDeleted) <>(Profile.tupled, Profile.unapply)

  def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(surname), Rep.Some(phoneExt), Rep.Some(phone), Rep.Some(nationality), Rep.Some(photo), Rep.Some(isDeleted)).shaped.
    <>({ r => import r._; _1.map(_ => Profile.tupled((_1.get, _2.get,_3.get,_4.get,_5.get,_6.get,_7.get,_8.get))) },
      (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  override val id: Rep[Long] = column[Long]("ProfileId", O.AutoInc, O.PrimaryKey)
  val name: Rep[String] = column[String]("name")
  val surname: Rep[String] = column[String]("surname")
  val phoneExt: Rep[String] = column[String]("phoneExt")
  val phone: Rep[String] = column[String]("phone")
  val nationality: Rep[String] = column[String]("nationality")
  val photo: Rep[String] = column[String]("photo")
  override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}

