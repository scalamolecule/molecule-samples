package app.h2

import app.dataModel.schema.PersonSchema
import molecule.core.marshalling.JdbcProxy
import molecule.core.spi.Conn
import molecule.sql.core.facade.{JdbcConn_JVM, JdbcHandler_JVM}
import scala.language.implicitConversions
import scala.util.Random
import scala.util.control.NonFatal

trait Connection {

  val schema = PersonSchema

  def types[T](test: Conn => T): T = {
    val url                = s"jdbc:h2:mem:test_database_" + Random.nextInt()
    val proxy              = JdbcProxy(
      url,
      schema.sqlSchema_h2,
      schema.metaSchema,
      schema.nsMap,
      schema.attrMap,
      schema.uniqueAttrs
    )
    var conn: JdbcConn_JVM = null
    try {
      Class.forName("org.h2.Driver")
      conn = JdbcHandler_JVM.recreateDb(proxy)
      test(conn)
    } catch {
      case NonFatal(exc) => throw exc
    } finally {
      if (conn != null && conn.sqlConn != null) {
        conn.sqlConn.close()
      }
    }
  }
}
