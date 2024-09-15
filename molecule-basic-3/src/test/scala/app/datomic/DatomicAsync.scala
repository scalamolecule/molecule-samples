package app.datomic

import app.dataModel.dsl.Person._
import app.dataModel.schema.PersonSchema
import molecule.core.util.Executor._
import molecule.datalog.datomic.async._
import molecule.datalog.datomic.facade.{DatomicConn_JVM, DatomicPeer}
import utest._
import scala.concurrent.Future

object DatomicAsync extends TestSuite {

  def futConn: Future[DatomicConn_JVM] = DatomicPeer.recreateDb(PersonSchema)

  override lazy val tests = Tests {

    "async" - {
      futConn.flatMap { implicit conn =>
        for {
          _ <- Person.name("Bob").age(42).save.transact
          _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
        } yield ()
      }
    }
  }
}
