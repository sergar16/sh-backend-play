package repository

import entity.scala.{Profile, ProfileTable, ProfileTeam, ProfileTeamsTable}
import repository.generic.BaseRepository
import slick.driver.H2Driver.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

/**
  * Created by Serhii on 8/18/2016.
  */
class ProfileRepository extends BaseRepository[ProfileTable, Profile](TableQuery[ProfileTable]){
  def getByUserId(userId:Long): Unit ={
    filter(_.userId===userId)
  }
}

class ProfileTeamsRepository extends BaseRepository[ProfileTeamsTable, ProfileTeam](TableQuery[ProfileTeamsTable]) {

}