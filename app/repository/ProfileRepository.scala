package repository

import entity.scala.{Profile, ProfileTable, Profiles, Users}
import repository.generic.BaseRepository
import slick.lifted.TableQuery

/**
  * Created by serhii.hokhkalenko on 2016-11-07.
  */
class ProfileRepository extends BaseRepository[ProfileTable, Profile](TableQuery[ProfileTable]){
 }
