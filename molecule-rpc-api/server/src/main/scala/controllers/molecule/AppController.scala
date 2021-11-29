package controllers.molecule

import java.nio.ByteBuffer
import boopickle.Default._
import cats.implicits._
import molecule.core.marshalling.MoleculeRpcHandler
import play.api.mvc.{Action, AnyContent, InjectedController, RawBuffer}
import server.PersonImpl
import server.page.AppPage
import server.util.HtmlTag
import shared.PersonApi
import sloth._
import scala.concurrent.Future


class AppController extends MoleculeRpcHandler("localhost", 9000)
  with InjectedController with HtmlTag {

  def testPage: Action[AnyContent] = Action(Ok(AppPage()))


  val router: Router[ByteBuffer, Future] = Router[ByteBuffer, Future]
    .route[PersonApi](PersonImpl)  // rpc api routing to server implementation

  def ajax(path: String): Action[RawBuffer] = {
    Action.async(parse.raw) { implicit ajaxRequest =>
      val args = ajaxRequest.body.asBytes(parse.UNLIMITED).get
      moleculeRpcResult(router, path, args).map(Ok(_))
    }
  }
}