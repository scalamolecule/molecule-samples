package app.datomic

import app.TestSetup
import app.domain.dsl.Person._
import molecule.datalog.datomic.zio._

class Datomic_zio extends TestSetup {

  "zio" - datomic { implicit conn =>
    runZIO(Person.name("Bob").age(42).save.transact)

    // ZIO[Conn, MoleculeError, List[(String, Int)]]
    runZIO(Person.name.age.query.get) ==> List(("Bob", 42))
  }
}
