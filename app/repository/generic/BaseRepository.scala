package repository.generic

import entity.scala.{BaseEntity, BaseTable}
import slick.driver.H2Driver
import slick.driver.H2Driver.api._
import slick.lifted.{CanBeQueryCondition, Rep}

import scala.concurrent.Future
import scala.reflect.{ClassTag, classTag}
/**
  * Created by Serhii on 8/18/2016.
  */
abstract class BaseRepository[T <: BaseTable[E], E <: BaseEntity : ClassTag](clazz: TableQuery[T]) extends BaseRepositoryQuery[T, E] with BaseRepositoryComponent[T,E] {

  val clazzTable: TableQuery[T] = clazz
  lazy val clazzEntity = classTag[E].runtimeClass
  val query: H2Driver.api.type#TableQuery[T] = clazz
  val db= Database.forConfig("h2mem1")

  def getAll: Future[Seq[E]] = {
    db.run(getAllQuery.result)
  }

  def getById(id: Long): Future[Option[E]] = {
    db.run(getByIdQuery(id).result.headOption)
  }

  def filter[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]) = {
    db.run(filterQuery(expr).result)
  }

  def save(row: E) = {
    db.run(saveQuery(row))
  }

  def updateById(id: Long, row: E) = {
    db.run(updateByIdQuery(id, row))
  }

  def deleteById(id: Long) = {
    db.run(deleteByIdQuery(id))
  }

}
