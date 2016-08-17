package entity.scala

import slick.lifted.{Rep, Tag}
import slick.driver.H2Driver.api._
import scala.reflect.{ClassTag,classTag}

/**
  * Created by Serhii on 8/18/2016.
  */

trait BaseEntity {
  val id: Long
  val isDeleted: Boolean
}

abstract class BaseTable[E: ClassTag](tag: Tag, schemaName: Option[String], tableName: String)
  extends Table[E](tag, schemaName, tableName) {
  val classOfEntity = classTag[E].runtimeClass
  val id: Rep[Long] = column[Long]("Id", O.PrimaryKey, O.AutoInc)
  val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
}
