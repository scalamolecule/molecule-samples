package app

import java.sql.DriverManager
import app.domain.schema.{PersonSchema_datomic, PersonSchema_h2}
import molecule.base.error.MoleculeError
import molecule.core.marshalling.*
import molecule.core.spi.Conn
import molecule.core.util.Executor.global
import molecule.datalog.datomic.facade.DatomicPeer
import molecule.sql.core.facade.JdbcHandler_JVM
import munit.FunSuite
import zio.{Runtime, Unsafe, ZEnvironment, ZIO}
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.util.Random
import scala.util.Using.Manager


trait TestSetup extends FunSuite {

  def h2[T](test: Conn => T): T = {
    val url = "jdbc:h2:mem:test" + Random.nextInt().abs
    Class.forName("org.h2.Driver") // Explicitly load the driver
    Manager { use =>
      val proxy   = JdbcProxy_h2(url, PersonSchema_h2)
      val sqlConn = use(DriverManager.getConnection(proxy.url))
      val conn    = use(JdbcHandler_JVM.recreateDb(proxy, sqlConn))
      test(conn)
    }.get
  }


  def datomic[T](test: Conn => T): T = {
    val conn = Await.result(DatomicPeer.recreateDb(PersonSchema_datomic), 1.second)
    test(conn)
  }


  // Some helper functions to make tests simpler

  implicit class TestableString(s: String) {
    def -(x: => Any): Unit = test(s)(x)
  }

  implicit class ArrowAssert(lhs: Any) {
    def ==>[V](rhs: V): Unit = assertEquals(lhs, rhs)
  }

  // Choosing a simple conversion to block each action for easier testing
  def runZIO[A](io: ZIO[Conn, MoleculeError, A])(implicit conn: Conn): A = {
    Unsafe.unsafe { implicit unsafe =>
      val runtime = Runtime.default
      runtime.unsafe.run(io.provideEnvironment(ZEnvironment(conn))).getOrThrow()
    }
  }
}
