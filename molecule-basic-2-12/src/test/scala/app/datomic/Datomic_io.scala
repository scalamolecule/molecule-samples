package app.datomic

import app.dataModel.dsl.Person._
import app.dataModel.schema.PersonSchema
import molecule.datalog.datomic.facade.DatomicPeer
import molecule.datalog.datomic.io._
import munit.CatsEffectSuite

// (notice that munit uses class whereas utest uses object)
class Datomic_io extends CatsEffectSuite {

  test("io") {
    DatomicPeer.recreateDbIO(PersonSchema).flatMap { implicit conn =>
      for {
        _ <- Person.name("Bob").age(42).save.transact
        _ <- Person.name.age.query.get.map(result => assertEquals(result, List(("Bob", 42))))
      } yield ()
    }
  }
}