package app.h2

import app.TestSetup
import app.domain.dsl.Person.*
import molecule.base.error.MoleculeError
import molecule.db.h2.Zio.*

class H2_zio extends TestSetup {

  "zio" - h2 {
    runZIO(Person.name("Bob").age(42).save.transact)

    runZIO(Person.name.age.query.get) ==> List(("Bob", 42))
  }
}
