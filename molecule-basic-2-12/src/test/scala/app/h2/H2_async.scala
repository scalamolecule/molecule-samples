package app.h2

import app.TestSetup
import app.domain.dsl.Person._
import molecule.core.util.Executor._
import molecule.sql.h2.async._
import scala.language.implicitConversions

class H2_async extends TestSetup {

  "async" - h2 { implicit conn =>
    for {
      _ <- Person.name("Bob").age(42).save.transact

      // Future[List[(String, Int)]]
      _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
    } yield ()
  }
}
