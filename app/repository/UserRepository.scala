package repository

import entity.scala._
import repository.generic.BaseRepository
import slick.lifted.TableQuery
import slick.driver.H2Driver.api._
import scala.concurrent.duration._

import scala.concurrent.{Await, Future}

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

  def getByLoginAndPassword(login:String,password:String): Future[Option[User]] = {
   filterSingleResult(_.email === login)
  }

  def getAllUsers():Future[Seq[User]]={
    getAll
  }
}

object UserRepository{
  //todo: remove it
  def initDb(): Unit ={
    val db = Database.forConfig("h2mem1")

    val users = Users.users;


    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      (users.schema).create,
      users+= User(1,"sergar16", "serhii.hokhkalenko@gmail.com","1111")
    )
    val setupFuture = Await.result(db.run(setup), 10.seconds)

    /////////////////////////////////////
  }
}
