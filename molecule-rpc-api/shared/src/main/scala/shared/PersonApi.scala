package shared

import scala.concurrent.Future

// Shared api
trait PersonApi {
  def getPersonAge(name: String): Future[Int]
}


