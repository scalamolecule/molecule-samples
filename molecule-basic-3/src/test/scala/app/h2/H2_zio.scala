package app.h2

import app.TestSetup
import app.domain.dsl.Person.*
import molecule.base.error.MoleculeError
import molecule.core.spi.Conn
import molecule.sql.h2.zio.*

class H2_zio extends TestSetup {

  "zio" - h2 { implicit conn =>
    runZIO(Person.name("Bob").age(42).save.transact)

    runZIO(Person.name.age.query.get) ==> List(("Bob", 42))
  }
}
