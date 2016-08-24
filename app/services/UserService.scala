package services

import com.google.inject.Inject
import repository.UserRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Serhii on 8/24/2016.
  */
class UserService @Inject()(userRepository: UserRepository)(implicit exec: ExecutionContext) {

  def auth(login:String,password:String): Future[Boolean]={
    userRepository.getByLogin(login).map{
      case Some(user) => password.equals(user.password)
      case None => false
    }
  }
}
