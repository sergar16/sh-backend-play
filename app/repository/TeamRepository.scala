package repository

import entity.scala.{Team, TeamTable}
import repository.generic.BaseRepository
import slick.driver.H2Driver.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

/**
  * Created by Serhii on 8/18/2016.
  */
class TeamRepository extends BaseRepository[TeamTable, Team](TableQuery[TeamTable]){
}
