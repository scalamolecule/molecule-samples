package app.datomic

import app.dataModel.dsl.Person._
import app.dataModel.schema.PersonSchema
import molecule.datalog.datomic.facade.DatomicPeer
import molecule.datalog.datomic.zio._
import zio._
import zio.test.TestAspect._
import zio.test._

object Datomic_zio extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("Datomic")(
      test("zio") {
        for {
          _ <- Person.name("Bob").age(42).save.transact
          result <- Person.name.age.query.get
        } yield {
          assertTrue(result == List(("Bob", 42)))
        }
      }.provide(DatomicPeer.recreateDbZLayer(PersonSchema).orDie),
    ) @@ sequential
}
