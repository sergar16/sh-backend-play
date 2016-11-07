package repository

import entity.scala.{Profile, ProfileTable, UsersFriends, UsersFriendsTable}
import repository.generic.BaseRepository
import slick.lifted.TableQuery

/**
  * Created by serhii.hokhkalenko on 2016-11-07.
  */
class UsersFriendsRepository extends BaseRepository[UsersFriendsTable, UsersFriends](TableQuery[UsersFriendsTable]){
 }
