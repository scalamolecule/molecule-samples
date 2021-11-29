package server.util

import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.mvc.Codec
import scalatags.Text.all.Tag

/**
 * Allow Play Actions to receive Scalatags like this:
 *
 * Ok(someScalaTag)
 *
 * instead of
 *
 * Ok(someScalaTag.render).as("text/html")
 *
 * Let your controller extend this class
 * */
trait HtmlTag {

  implicit def contentTypeOfTag(implicit codec: Codec): ContentTypeOf[Tag] = {
    ContentTypeOf[Tag](Some(ContentTypes.HTML))
  }

  implicit def writeableOfTag(implicit codec: Codec): Writeable[Tag] = {
    Writeable(tag => codec.encode("<!DOCTYPE html>\n" + tag.render))
  }
}