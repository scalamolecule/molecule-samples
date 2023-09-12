package app.jdbc

import app.schema.PersonDataModelSchema
import molecule.core.marshalling.JdbcProxy
import molecule.core.spi.Conn
import molecule.sql.jdbc.facade.{JdbcConn_JVM, JdbcHandler_JVM}

import scala.language.implicitConversions
import scala.util.Random
import scala.util.control.NonFatal

trait Helper {

  val schema = PersonDataModelSchema

  def types[T](test: Conn => T): T = {
    val url   = s"jdbc:h2:mem:test_database_" + Random.nextInt()
    val proxy = JdbcProxy(
      url,
      schema.sqlSchema("h2"),
      schema.metaSchema,
      schema.nsMap,
      schema.attrMap,
      schema.uniqueAttrs
    )
    var conn  = JdbcConn_JVM(proxy, null)
    try {
      Class.forName("org.h2.Driver")
      conn = JdbcHandler_JVM.recreateDb(proxy)
      test(conn)
    } catch {
      case NonFatal(exc) => throw exc
    } finally {
      if (conn.sqlConn != null) {
        conn.sqlConn.close()
      }
    }
  }
}
