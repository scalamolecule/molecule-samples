package app

import app.dsl.Person._
import app.schema.PersonSchema
import molecule.datomic.api._
import molecule.datomic.base.facade.Conn
import molecule.datomic.client.facade.Datomic_PeerServer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}


object SampleApp extends App {

  // Make sure to first follow the instructions in the README to start the Peer Server:
  // - Start dev transactor in one process
  // - Create `sampleDb` with Peer shell
  // - Start Peer Server, serving `sampleDb`

  // Connect to Peer Server
  implicit val futConn: Future[Conn] = {
    println("Connecting to Peer Server `sampleDb`...")
    Datomic_PeerServer("k", "s", "localhost:8998")
      .connect(PersonSchema, "sampleDb")
      // Transact schema (sampleDb is created but empty on first run)
      .flatMap { conn =>
        conn.transact(PersonSchema.datomicClient.head)
          .map { _ =>
            // Make sure that Peer Server schema transaction has propagated
            Thread.sleep(2000)
            conn
          }
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
