package app.h2

import app.dataModel.dsl.Person._
import molecule.core.marshalling.JdbcProxy
import molecule.sql.core.facade.JdbcHandler_JVM
import molecule.sql.h2.io._
import munit.CatsEffectSuite
import scala.util.Random

// (notice that munit uses class whereas utest uses object)
class H2_io extends CatsEffectSuite with Connection {

  test("io") {
    implicit val conn = {
      val url   = s"jdbc:h2:mem:test_database_" + Random.nextInt()
      val proxy = JdbcProxy(
        url,
        schema.sqlSchema_h2,
        schema.metaSchema,
        schema.nsMap,
        schema.attrMap,
        schema.uniqueAttrs
      )
      Class.forName("org.h2.Driver")
      JdbcHandler_JVM.recreateDb(proxy)
    }

    for {
      _ <- Person.name("Bob").age(42).save.transact
      _ <- Person.name.age.query.get.map(result => assertEquals(result, List(("Bob", 42))))
    } yield ()
  }
}
