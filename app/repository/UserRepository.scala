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
    val profiles = Profiles.profiles;
    val usersFriends = UsersFriendss.usersFriendss;


    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      (users.schema ++ profiles.schema ++ usersFriends.schema).create,
      //Serhii
      profiles += Profile(1, "Serhii", "Hokhkalenko", "+3900", "324234", "234234", "rwerwe"),
      users += User(1, 1, "sergar16", "serhii.hokhkalenko@gmail.com", "1111"),

      //Adnrii
        profiles += Profile(1, "Andrii", "Herula", "+3900", "234234", "234234234", "rwer234we"),
      users += User(2, 1, "zviryok", "genuk@gmail.com", "1111"),

      //Bosovych
      profiles += Profile(1, "Oleg", "Bosovych", "+3900", "234234", "234234234", "rwer234we"),
      users += User(3, 1, "boss", "likeABoss@gmail.com", "1111"),

      //Romko
      profiles += Profile(1, "Roman", "Leva", "+3900", "234234", "234234234", "rwer234we"),
      users += User(3, 1, "lewa", "levandowski@gmail.com", "1111"),

      usersFriends+=UsersFriends(1,1,2),
      usersFriends+=UsersFriends(2,1,3),
      usersFriends+=UsersFriends(3,1,4),
      usersFriends+=UsersFriends(4,2,4)
    )
    val setupFuture = Await.result(db.run(setup), 10.seconds)

    /////////////////////////////////////
  }
}
