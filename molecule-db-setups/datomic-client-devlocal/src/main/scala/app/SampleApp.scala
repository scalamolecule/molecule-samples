package app

import app.dsl.Person._
import app.schema.PersonSchema
import molecule.datomic.api._
import molecule.datomic.client.facade.Datomic_DevLocal
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt


object SampleApp extends App {

  val protocol     = "mem"
  val dbName       = "sampleDb"
  val dbIdentifier = "localhost:4334/sampleDb"
  val devLocal     = Datomic_DevLocal("datomic-samples")

  // Get database connection
  implicit val conn = {
    println("Connecting to `sampleDb`...")
    devLocal.connect(PersonSchema, protocol, dbName)
      .recoverWith { _ =>
        // Create db if not yet created (on first run)
        println("Creating `sampleDb`...")
        devLocal.recreateDbFrom(PersonSchema, dbName)
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
