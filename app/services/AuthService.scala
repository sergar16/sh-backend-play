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

    def auth(login:String,password:String): Future[Boolean]={
     userRepository.getByLogin(login).map{
        case Some(user) => password.equals(user.password)
        case None => false
      }
    }
}
