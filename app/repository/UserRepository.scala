package repository

import entity.scala._
import repository.generic.BaseRepository
import slick.lifted.TableQuery
import slick.driver.H2Driver.api._


import scala.concurrent.Future

/**
  * Created by Serhii on 8/18/2016.
  */
class UserRepository extends BaseRepository[UserTable, User](TableQuery[UserTable]){
   def getByLogin(login: String): Future[Option[User]] = {
    filterSingleResult(_.login === login)
  }
  def getByEmail(login: String): Future[Option[User]] = {
    filterSingleResult(_.email === login)
  }

}
