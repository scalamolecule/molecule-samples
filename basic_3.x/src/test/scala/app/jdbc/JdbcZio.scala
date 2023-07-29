package app.jdbc

import app.dsl.PersonDataModel._
import app.schema.PersonDataModelSchema
import molecule.core.marshalling.JdbcProxy
import molecule.core.spi.Conn
import molecule.sql.jdbc.facade.JdbcHandler_jvm
import molecule.sql.jdbc.spi.JdbcSpiZio
import molecule.sql.jdbc.zio._
import zio._
import zio.test.TestAspect._
import zio.test._
import scala.util.Random

object JdbcZio extends ZIOSpecDefault {

  // Convert Datomic-idiomatic blocking jdbc to ZIO Layer
  def personLayer[T]: ZLayer[T, Throwable, Conn] = {
    val schema = PersonDataModelSchema
    val url   = s"jdbc:h2:mem:test_database_" + Random.nextInt()
    val proxy = JdbcProxy(
      url,
      schema.sqlSchema("h2"),
      schema.metaSchema,
      schema.nsMap,
      schema.attrMap,
      schema.uniqueAttrs
    )
    ZLayer.scoped(
      ZIO.attemptBlocking(
        JdbcHandler_jvm.recreateDb(proxy, url)
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
