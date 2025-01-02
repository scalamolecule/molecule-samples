package app.h2

import app.TestSetup
import app.domain.dsl.Person._
import molecule.sql.h2.io._

class H2_io extends TestSetup {

  "io" - h2 { implicit conn =>
    for {
      _ <- Person.name("Bob").age(42).save.transact

      // cats.effect.IO[List[(String, Int)]]
      _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
    } yield ()
  }
}
