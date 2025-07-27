package app.h2

import app.TestSetup
import app.domain.dsl.Person.*
import molecule.db.h2.io.*

class H2_io extends TestSetup {

  "io" - h2 {
    for {
      _ <- Person.name("Bob").age(42).save.transact

      // cats.effect.IO[List[(String, Int)]]
      _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
    } yield ()
  }
}
