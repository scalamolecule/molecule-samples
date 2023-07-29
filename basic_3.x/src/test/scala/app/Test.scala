package app

import app.dsl.PersonDataModel._
import app.schema.PersonDataModelSchema
import molecule.core.util.Executor._
import molecule.datalog.datomic.async._
import molecule.datalog.datomic.facade.{DatomicConn_JVM, DatomicPeer}
import utest._
import scala.concurrent.Future

object Test extends TestSuite {

  def futConn: Future[DatomicConn_JVM] = DatomicPeer.recreateDb(PersonDataModelSchema)

  override lazy val tests = Tests {

    "test" - {
      futConn.flatMap { implicit conn =>
        for {
          _ <- Person.name("Bob").age(42).save.transact
          _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
        } yield ()
      }
    }
  }
}
