package app

import app.dsl.Person._
import app.schema.PersonSchema
import molecule.datomic.api._
import molecule.datomic.client.facade.Datomic_PeerServer
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

// See project readme for more info...

object SampleApp extends App {

  // Re-connect to running peer server db (see README for setup)
  implicit val futConn = Datomic_PeerServer("k", "s", "localhost:8998").connect(PersonSchema, "sampleDb")

  Await.result(
    for {
      conn <- futConn

      // Transact schema for each fresh in-mem connection
      _ <- conn.transact(PersonSchema.datomicClient.head)

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
