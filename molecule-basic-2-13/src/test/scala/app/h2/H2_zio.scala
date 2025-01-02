package app.h2

import app.TestSetup
import app.domain.dsl.Person._
import molecule.sql.h2.zio._

class H2_zio extends TestSetup {

  "zio" - h2 { implicit conn =>
    runZIO(Person.name("Bob").age(42).save.transact)

    // ZIO[Conn, MoleculeError, List[(String, Int)]]
    runZIO(Person.name.age.query.get) ==> List(("Bob", 42))
  }
}
