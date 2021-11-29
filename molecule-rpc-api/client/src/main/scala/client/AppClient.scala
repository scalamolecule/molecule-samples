package client

import boopickle.Default._
import molecule.core.marshalling.WebClient
import org.scalajs.dom.document.body
import scalatags.JsDom.all._
import shared.PersonApi
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("AppClient")
object AppClient extends WebClient {
  // Manual rpc gateway, using our shared Person api
  val manualRpc: PersonApi = moleculeAjax("localhost", 9000).wire[PersonApi]

  @JSExport
  def load(): Unit = {

    // Manual rpc via shared interface and server implementation
    manualRpc.getPersonAge("Bob").map(age =>
      body.appendChild(div("Bob's age: " + age).render)
    )
  }
}
