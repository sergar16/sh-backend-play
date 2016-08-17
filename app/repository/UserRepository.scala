package repository

import entity.scala.{User, UserTable, Users}
import slick.lifted.TableQuery

/**
  * Created by Serhii on 8/18/2016.
  */
class UserRepository extends BaseRepository[UserTable, User](TableQuery[UserTable])