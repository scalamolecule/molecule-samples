package app.h2

import app.dsl.PersonDataModel._
import molecule.sql.h2.sync._
import utest._
import scala.language.implicitConversions

object H2Sync extends TestSuite with Helper {

  override lazy val tests = Tests {

    "sync" - types { implicit conn =>
      Person.name("Bob").age(42).save.transact
      Person.name.age.query.get ==> List(("Bob", 42))
    }


    // Inspect molecule transformations

    "inspect" - types { implicit conn =>

      // Inspection of transactions not yet implemented
      // Person.name("Bob").age(42).save.inspect


      Person.name.age.query.inspect
      /*
        SELECT DISTINCT
          Person.name,
          Person.age
        FROM Person
        WHERE
          Person.name IS NOT NULL AND
          Person.age  IS NOT NULL;
     */
    }
  }
}
