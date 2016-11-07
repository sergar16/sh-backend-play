package services

import com.google.inject.Inject
import entity.scala.User
import repository.{UserRepository, UsersFriendsRepository}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Serhii on 8/24/2016.
  */
class UsersFriendsService @Inject()(usersFriendsRepository: UsersFriendsRepository)(implicit exec: ExecutionContext) {
  //todo : realize adding, deleting and list of friends

}
