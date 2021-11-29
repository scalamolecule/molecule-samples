package client

import boopickle.Default._
import db.dsl.Person._
import db.schema.PersonSchema
import molecule.core.facade.Conn_Js
import molecule.core.marshalling.DatomicPeerProxy
import molecule.datomic.api._
import org.scalajs.dom.document.body
import scalatags.JsDom.all._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("AppClient")
object AppClient {
  // Create in-mem database connection using our Person data model
  implicit val conn = Future(Conn_Js(
    DatomicPeerProxy("mem", "", PersonSchema.datomicPeer, PersonSchema.attrMap), "localhost", 9000
  ))

  @JSExport
  def load(): Unit = {

    // Save/get data transparently with one or more molecules
    for {
      // Save data
      _ <- Person.name("Bob").age(42).save

      // Get data
      age <- Person.name_("Bob").age.get.map(_.head)
    } yield {
      body.appendChild(div("Bob's age: " + age).render)
    }


    // With a populated db, we could make a single molecule call
    //    Person.name_("Bob").age.get.map(age =>
    //      document.body.appendChild(div("Bob's age: " + age).render)
    //    )
  }
}
