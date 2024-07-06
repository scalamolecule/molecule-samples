package app.h2

import app.dsl.Person.*
import app.schema.PersonSchema
import molecule.core.marshalling.JdbcProxy
import molecule.core.spi.Conn
import molecule.sql.core.facade.JdbcHandler_JVM
import molecule.sql.h2.zio.*
import zio.*
import zio.test.*
import zio.test.TestAspect.*
import scala.util.Random

object H2Zio extends ZIOSpecDefault {

  // Convert Datomic-idiomatic blocking jdbc to ZIO Layer
  def personLayer[T]: ZLayer[T, Throwable, Conn] = {
    val schema = PersonSchema
    val url    = s"jdbc:h2:mem:test_database_" + Random.nextInt()
    val proxy  = JdbcProxy(
      url,
      schema.sqlSchema_h2,
      schema.metaSchema,
      schema.nsMap,
      schema.attrMap,
      schema.uniqueAttrs
    )
    ZLayer.scoped(
      ZIO.attemptBlocking(
        JdbcHandler_JVM.recreateDb(proxy)
      )
    )
  }


  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("JDBC")(
      test("zio") {
        for {
          _ <- Person.name("Bob").age(42).save.transact
          result <- Person.name.age.query.get
        } yield {
          assertTrue(result == List(("Bob", 42)))
        }
      }.provide(personLayer.orDie),
    ) @@ sequential
}
