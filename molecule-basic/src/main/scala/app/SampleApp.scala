package app

import app.dsl.Person._
import app.schema.PersonSchema
import molecule.datomic.api._
import molecule.datomic.peer.facade.Datomic_Peer
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt


object SampleApp extends App {

  // Re-create in-mem database
  implicit val conn = Datomic_Peer.recreateDbFrom(PersonSchema)

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
