package repository
import entity.scala.{BaseEntity, BaseTable}
import slick.driver.H2Driver
import slick.lifted.{CanBeQueryCondition, Rep, Tag}
import slick.driver.H2Driver.api._

import scala.concurrent.Future
/**
  * Created by Serhii on 8/18/2016.
  */
trait BaseRepositoryQuery[T <: BaseTable[E], E <: BaseEntity] {

  val query: H2Driver.api.type#TableQuery[T]

  def getByIdQuery(id: Long) = {
    query.filter(_.id === id).filter(_.isDeleted === false)
  }

  def getAllQuery = {
    query.filter(_.isDeleted === false)
  }

  def filterQuery[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]) = {
    query.filter(expr).filter(_.isDeleted === false)
  }

  def saveQuery(row: E) = {
    query returning query += row
  }

  def deleteByIdQuery(id: Long) = {
    query.filter(_.id === id).map(_.isDeleted).update(true)
  }

  def updateByIdQuery(id: Long, row: E) = {
    query.filter(_.id === id).filter(_.isDeleted === false).update(row)
  }

}