package repository.generic

import entity.scala.{BaseEntity, BaseTable}
import slick.lifted.{CanBeQueryCondition, Rep}

import scala.concurrent.Future
/**
  * Created by Serhii on 8/18/2016.
  */

trait BaseRepositoryComponent[T <: BaseTable[E], E <: BaseEntity] {
  def getById(id: Long) : Future[Option[E]]
  def getAll : Future[Seq[E]]
  def filter[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]): Future[Seq[E]]
  def save(row: E) : Future[E]
  def deleteById(id: Long) : Future[Int]
  def updateById(id: Long, row: E) : Future[Int]
}
