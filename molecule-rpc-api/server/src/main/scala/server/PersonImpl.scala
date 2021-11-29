package server

import db.dsl.Person._
import db.schema.PersonSchema
import molecule.datomic.api._
import molecule.datomic.peer.facade.Datomic_Peer
import shared.PersonApi
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// Server implementation of shared api
object PersonImpl extends PersonApi {
//import org.joda.
  def getPersonAge(name: String): Future[Int] = {
    // Create in-mem db (no need for proxy since we are on server here)
    implicit val conn = Datomic_Peer.recreateDbFrom(PersonSchema)

    for{
      // Save data
      _ <- Person.name("Bob").age(42).save

      // Get data
      age <- Person.name_(name).age.get.map(_.head)
    } yield age
  }
}
