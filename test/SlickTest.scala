import entity.scala.Player
import play.api.test.FakeApplication
import slick.driver.H2Driver.api._
import play.api.Logger
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by serhii.hokhkalenko on 2016-08-16.
  */

object SlickTest {
  FakeApplication(additionalConfiguration = inMemoryDatabase())
  val players = Player.players;
  val db = Database.forConfig("h2mem1")
  def main(args: Array[String]): Unit = {

    try {
      val setup = DBIO.seq(
        // Create the tables, including primary and foreign keys

        (players.schema).create,

        // Insert some suppliers
        players +=(1, "Mesut", "Ozil", 11),
        players +=(2, "Alexis", "Sanches", 17),
        players +=(3, "Jack", "Wilshere", 10)
        // Equivalent SQL code:
        // insert into SUPPLIERS(SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP) values (?,?,?,?,?,?)
        // Equivalent SQL code:
        // insert into COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL) values (?,?,?,?,?)
      )

      val setupFuture = db.run(setup)

      db.run(players.result).map(_.foreach {
        case (id, name, surname, number) =>
          println("  " + id + "\t" + name + "\t" + surname + "\t" + number )
      })
    } finally db.close
    }
}
