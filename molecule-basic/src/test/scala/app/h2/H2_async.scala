package app.h2

import app.TestSetup
import app.domain.dsl.Person.*
import molecule.db.common.util.Executor.*
import molecule.db.h2.async.*
import scala.language.implicitConversions

class H2_async extends TestSetup {

  "async" - h2 {
    for {
      _ <- Person.name("Bob").age(42).save.transact

      // Future[List[(String, Int)]]
      _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
    } yield ()
  }
}
