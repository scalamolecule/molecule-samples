package app.h2

import app.dataModel.dsl.Person._
import molecule.sql.h2.sync._
import utest._
import scala.language.implicitConversions

object H2_sync extends TestSuite with Connection {

  override lazy val tests = Tests {

    "sync" - types { implicit conn =>
      Person.name("Bob").age(42).save.transact
      Person.name.age.query.get ==> List(("Bob", 42))
    }


    "inspect" - types { implicit conn =>

      Person.name("Bob").age(42).save.inspect
      /*
        INSERT INTO Person (
          name,
          age
        ) VALUES (?, ?)
       */


      Person.name.age.query.inspect
      /*
        ========================================
        QUERY:
        AttrOneManString("Person", "name", V, Seq(), None, None, Nil, Nil, None, None, Seq(0, 1))
        AttrOneManInt("Person", "age", V, Seq(), None, None, Nil, Nil, None, None, Seq(0, 2))

        SELECT DISTINCT
          Person.name,
          Person.age
        FROM Person
        WHERE
          Person.name IS NOT NULL AND
          Person.age  IS NOT NULL;
        ----------------------------------------
     */
    }
  }
}
