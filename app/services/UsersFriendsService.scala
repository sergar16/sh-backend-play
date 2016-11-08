package services

import com.google.inject.Inject
import entity.scala._
import repository.{UserRepository, UsersFriendsRepository}
import slick.lifted.Query

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

/**
  * Created by Serhii on 11/7/2016.
  */
class UsersFriendsService @Inject()(usersFriendsRepository: UsersFriendsRepository, userRepository: UserRepository)(implicit exec: ExecutionContext) {
  //todo : implement adding, deleting and list of friends
  def addFriendToUser(userId: Long, userFriendId: Long) {
    //todo : implement autoIncrement supporting
    usersFriendsRepository.save(UsersFriends(Random.nextInt, userId, userFriendId))
  }


  def getUserFriendsList(userId:Long):Future[Seq[User]]= {
    usersFriendsRepository.getUserFriendsList(userId)
  }

}
