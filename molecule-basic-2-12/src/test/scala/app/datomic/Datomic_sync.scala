package app.datomic

import app.dataModel.dsl.Person._
import app.dataModel.schema.PersonSchema
import molecule.core.util.Executor._
import molecule.datalog.datomic.facade.{DatomicConn_JVM, DatomicPeer}
import molecule.datalog.datomic.sync._
import utest._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object Datomic_sync extends TestSuite {

  def futConn: Future[DatomicConn_JVM] = DatomicPeer.recreateDb(PersonSchema)

  override lazy val tests = Tests {

    "sync" - {
      implicit val conn: DatomicConn_JVM = Await.result(futConn, 10.second)

      Person.name("Bob").age(42).save.transact
      Person.name.age.query.get ==> List(("Bob", 42))
    }


    // Inspect molecule transformations

    "inspect" - {
      implicit val conn: DatomicConn_JVM = Await.result(futConn, 10.second)

      Person.name("Bob").age(42).save.inspect
      /*
        [:db/add, #db/id[db.part/user -1], :Person/name, Bob]
        [:db/add, #db/id[db.part/user -1], :Person/age, 42]
      */

      Person.name.age.query.inspect
      /*
        [:find  ?b ?c
         :where [?a :Person/name ?b]
                [?a :Person/age ?c]]
     */
    }
  }
}
