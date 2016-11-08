package repository

import entity.scala._
import repository.generic.BaseRepository
import slick.driver.H2Driver.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

/**
  * Created by serhii.hokhkalenko on 2016-11-07.
  */
class UsersFriendsRepository extends BaseRepository[UsersFriendsTable, UsersFriends](TableQuery[UsersFriendsTable]) {

  /**
    * This function get list of all users friends
    *
    * @param userId
    * @return
    */
  def getUserFriendsList(userId: Long): Future[Seq[User]] = {
    //todo : check friends connection from both sides
    val users = Users.users
    val usersFriendsForUser = UsersFriendss.usersFriendss.filter(_.userId === userId)
    db run (for {
      (u, uf) <- users joinLeft usersFriendsForUser on (_.id === _.userId)
    } yield u).result
  }
}
