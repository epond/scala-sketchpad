package sketchpad.core

import sketchpad.core.UserAPI.{APIKey, StreetAddress}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz._
import Scalaz._

object ReaderTransformer extends App {
  val apiKey = "123ABC"

  val streetAddressDirectly: Future[StreetAddress] = for {
    user <- UserAPI.getUser("Fred", apiKey)
    streetAddress <- UserAPI.getStreetAddress(user.email, apiKey)
  } yield streetAddress

  val streetAddressReader: ReaderT[Future, APIKey, StreetAddress] = for {
    user <- UserReaderAPI.getUser("Fred")
    streetAddress <- UserReaderAPI.getStreetAddress(user.email)
  } yield streetAddress
  val streetAddressFromReader: Future[StreetAddress] = streetAddressReader(apiKey)
}

case class User(name: UserAPI.Name, email: UserAPI.Email)

object UserAPI {
  type Email = String
  type Name = String
  type StreetAddress = String
  type APIKey = String

  def getUser(name: Name, apiKey: APIKey): Future[User] =
    Future(User("Fred", "fred@mail.com"))
  def getStreetAddress(email: String, apiKey: APIKey): Future[StreetAddress] =
    Future("1 Acacia Road")
}

object UserReaderAPI {
  import Kleisli._

  def getUser(name: UserAPI.Name): ReaderT[Future, UserAPI.APIKey, User] = {
    kleisli { apiKey =>
      UserAPI.getUser(name, apiKey)
    }
  }
  def getStreetAddress(email: String): ReaderT[Future, UserAPI.APIKey, UserAPI.StreetAddress] = {
    kleisli { apiKey =>
      UserAPI.getStreetAddress(email, apiKey)
    }
  }
}
