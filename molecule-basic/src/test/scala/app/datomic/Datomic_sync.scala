package app.datomic

import app.TestSetup
import app.domain.dsl.Person._
import molecule.datalog.datomic.sync._

class Datomic_sync extends TestSetup {

  "sync" - datomic { implicit conn =>
    Person.name("Bob").age(42).save.transact

    // List[(String, Int)]
    Person.name.age.query.get ==> List(("Bob", 42))
  }
}
