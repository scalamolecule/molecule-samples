package controllers.molecule

import java.nio.ByteBuffer
import boopickle.Default._
import cats.implicits._
import molecule.core.marshalling.{MoleculeRpc, MoleculeRpcResponse}
import molecule.datomic.base.marshalling.DatomicRpc
import play.api.mvc.{Action, AnyContent, InjectedController, RawBuffer}
import server.page.AppPage
import server.util.HtmlTag
import sloth._
import scala.concurrent.Future


class AppController extends MoleculeRpcResponse("localhost", 9000)
  with InjectedController with HtmlTag {

  def testPage: Action[AnyContent] = Action(Ok(AppPage()))


  val router: Router[ByteBuffer, Future] = Router[ByteBuffer, Future]
    .route[MoleculeRpc](DatomicRpc()) // internal transparent molecule rpc routing

  def ajax(path: String): Action[RawBuffer] = {
    Action.async(parse.raw) { implicit ajaxRequest =>
      val args = ajaxRequest.body.asBytes(parse.UNLIMITED).get
      moleculeRpcResponse(router, path, args.asByteBuffer).map(Ok(_))
    }
  }
}