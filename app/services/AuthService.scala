package services

import java.util.Optional

import com.google.inject.Inject
import entity.scala.User
import repository.UserRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Serhii on 8/23/2016.
  */
class AuthService @Inject()(userRepository: UserRepository)(implicit exec: ExecutionContext) {
  /**
    * This method get User by login and if the password matches return user
    * @param login
    * @param password
    * @return user - if user exist amd password matches
    */
    def auth(login:String,password:String): Future[Option[User]]={
     userRepository.getByLogin(login).map{
        case user:Option[User] => if (password.equals(user.get.password)) user else None
      }
    }
}
