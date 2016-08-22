package repository

import entity.scala.{Player, PlayerTable, User}
import repository.generic.BaseRepository
import slick.lifted.TableQuery
import slick.driver.H2Driver.api._
import scala.concurrent.Future

/**
  * Created by serhii.hokhkalenko on 2016-08-18.
  */
class PlayerRepository extends BaseRepository[PlayerTable, Player](TableQuery[PlayerTable]) {

  def getPlayersByTeamId(teamId: Long): Future[Seq[Player]] = {
    filter(_.teamId === teamId)
  }

}