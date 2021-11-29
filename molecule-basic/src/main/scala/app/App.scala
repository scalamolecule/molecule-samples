package app

import app.dsl.Person._
import app.schema.PersonSchema
import molecule.datomic.api._
import molecule.datomic.base.facade.Conn
import molecule.datomic.peer.facade.Datomic_Peer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt


object App {

  // Recreate in-mem Peer db connection
  implicit val conn: Future[Conn] = Datomic_Peer.recreateDbFrom(PersonSchema)

  def main(args: Array[String]): Unit = {
    // Force main thread not to terminate before test futures have terminated
    Await.result(
      for {
        // Save data
        _ <- Person.name("Bob").age(42).save

        // Get data
        age <- Person.name_("Bob").age.get.map(_.head)
      } yield {
        println("Bob's age: " + age) // Bob's age: 42
      },
      5.seconds
    )
  }
}
