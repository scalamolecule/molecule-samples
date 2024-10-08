package app.datomic

import app.dataModel.dsl.Person._
import app.dataModel.schema.PersonSchema
import molecule.core.util.Executor._
import molecule.datalog.datomic.async._
import molecule.datalog.datomic.facade.DatomicPeer
import utest._

object Datomic_async extends TestSuite {

  override lazy val tests = Tests {
    "async" - {
      DatomicPeer.recreateDb(PersonSchema).flatMap { implicit conn =>
        for {
          _ <- Person.name("Bob").age(42).save.transact
          _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
        } yield ()
      }
    }
  }
}
