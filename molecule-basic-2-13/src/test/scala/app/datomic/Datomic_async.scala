package app.datomic

import app.TestSetup
import app.domain.dsl.Person._
import molecule.core.util.Executor._
import molecule.datalog.datomic.async._

class Datomic_async extends TestSetup {

  "async" - datomic { implicit conn =>
    for {
      _ <- Person.name("Bob").age(42).save.transact

      // Future[List[(String, Int)]]
      _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
    } yield ()
  }
}
