package app.datomic

import app.dsl.Person.*
import app.schema.PersonSchema
import molecule.core.spi.Conn
import molecule.core.util.Executor.*
import molecule.datalog.datomic.facade.DatomicPeer
import molecule.datalog.datomic.zio.*
import zio.*
import zio.test.*
import zio.test.TestAspect.*

object DatomicZio extends ZIOSpecDefault {

  // Convert Datomic-idiomatic Future to ZIO Layer
  def personLayer[T]: ZLayer[T, Throwable, Conn] = {
    ZLayer.scoped(
      ZIO.fromFuture(
        _ => DatomicPeer.recreateDb(PersonSchema)
      )
    )
  }


  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("Datomic")(
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
