package server.page

import controllers.routes
import scalatags.Text
import scalatags.Text.all._

object AppPage {

  def apply(): Text.TypedTag[String] =
    html(
      head(
        script(
          tpe := "text/javascript",
          src := routes.Assets.at("client-fastopt/main.js").url
        )
      ),

      body(
        fontFamily := "courier",
        h2("Manual rpc via shared api to server molecule"),
        script(s"AppClient.load()")
      )
    )
}