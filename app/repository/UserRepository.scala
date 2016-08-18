package repository

import entity.scala.{Player, User, UserTable, Users}
import repository.generic.BaseRepository
import slick.lifted.TableQuery

import scala.concurrent.Future

/**
  * Created by Serhii on 8/18/2016.
  */
class UserRepository extends BaseRepository[UserTable, User](TableQuery[UserTable])
{
//  override def getById(id: Long): Future[Option[User]] = {
//    val superRes = super.getById(id)
//    //remove the password field with some dummy data while sending back
//    superRes.map(_.map(_.copy(password = "*****")))
//  }
}
