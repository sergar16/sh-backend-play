package services

import com.google.inject.Inject
import entity.scala.User
import repository.UserRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Serhii on 8/24/2016.
  */
class UserService @Inject()(userRepository: UserRepository)(implicit exec: ExecutionContext) {
  /**
    * This method returns user by id if it exist
    *
    * @param id
    * @return
    */
  def getById(id:Long):Future[Option[User]]={
    userRepository.getById(id)
  }

  /**
    * This method add new user
    *
    * @param user
    */
  def addUser(user:User){
    userRepository.save(user)
  }

  /**
    * This method updating existing user
    *
    * @param user
    */
  def updateUser(user: User){
    userRepository.updateById(user.id,user)
  }

  /**
    * This method deleting user
    *
    * @param id
    */
  def deleteUser(id:Long){
    userRepository.deleteById(id)
  }

  def getAllUsers:Future[Seq[User]]={
    userRepository.getAll
  }

}
