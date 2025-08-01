package app.h2

import app.TestSetup
import app.domain.dsl.Person._
import molecule.db.h2.sync._

class H2_sync extends TestSetup {

  "sync" - h2 {
    Person.name("Bob").age(42).save.transact

    // List[(String, Int)]
    Person.name.age.query.get ==> List(("Bob", 42))
  }
}
