package app.h2

import app.dataModel.dsl.Person._
import molecule.core.util.Executor._
import molecule.sql.h2.async._
import utest._
import scala.language.implicitConversions

object H2_async extends TestSuite with Connection {

  override lazy val tests = Tests {

    "async" - types { implicit conn =>
      for {
        _ <- Person.name("Bob").age(42).save.transact
        _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
      } yield ()
    }
  }
}
