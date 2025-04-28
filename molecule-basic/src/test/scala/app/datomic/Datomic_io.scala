package app.datomic

import app.TestSetup
import app.domain.dsl.Person._
import molecule.db.datalog.datomic.io._

class Datomic_io extends TestSetup {

  "io" - datomic { implicit conn =>
    for {
      _ <- Person.name("Bob").age(42).save.transact

      // cats.effect.IO[List[(String, Int)]]
      _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
    } yield ()
  }
}