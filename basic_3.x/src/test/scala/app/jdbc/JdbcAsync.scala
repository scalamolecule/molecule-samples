package app.jdbc

import app.dsl.PersonDataModel._
import molecule.core.util.Executor._
import molecule.sql.jdbc.async._
import utest._
import scala.language.implicitConversions

object JdbcAsync extends TestSuite with Helper {

  override lazy val tests = Tests {

    "async" - types { implicit conn =>
      for {
        _ <- Person.name("Bob").age(42).save.transact
        _ <- Person.name.age.query.get.map(_ ==> List(("Bob", 42)))
      } yield ()
    }
  }
}
