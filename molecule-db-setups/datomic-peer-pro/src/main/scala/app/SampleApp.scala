package app

import app.dsl.Person._
import app.schema.PersonSchema
import molecule.datomic.api._
import molecule.datomic.peer.facade.Datomic_Peer
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt


object SampleApp extends App {

  val protocol     = "dev" // (pro)
  val dbIdentifier = "localhost:4334/sampleDb"

  // Get database connection
  implicit val conn = {
    println("Connecting to `sampleDb`...")
    Datomic_Peer.connect(PersonSchema, protocol, dbIdentifier)
      .recoverWith { _ =>
        // Create db if not yet created (on first run)
        println("Creating `sampleDb`...")
        Datomic_Peer.recreateDbFrom(PersonSchema, protocol, dbIdentifier)
      }
  }

  Await.result(
    for {
      // Save data
      _ <- Person.name("Bob").age(42).save

      // Get data
      age <- Person.name_("Bob").age.get.map(_.head)
    } yield {
      println("SUCCESS: Bob's age is " + age) // SUCCESS: Bob's age is 42
    },
    10.seconds
  )
}
