package sketchpad.core

import sketchpad.core.UserAPI.{UserName, Email, APIKey, StreetAddress}

import scalaz._
import Scalaz._

object ReaderTransformer extends App {
  val apiKey = "123ABC"

  val streetAddressDirectly: Option[StreetAddress] = for {
    user <- UserAPI.getUser("Fred", apiKey)
    streetAddress <- UserAPI.getStreetAddress(user.email, apiKey)
  } yield streetAddress

  val streetAddressReader: ReaderT[Option, APIKey, StreetAddress] = for {
    user <- UserReaderAPI.getUser("Fred")
    streetAddress <- UserReaderAPI.getStreetAddress(user.email)
  } yield streetAddress
  val streetAddressFromReader: Option[StreetAddress] = streetAddressReader(apiKey)
}

case class User(name: UserName, email: Email)

object UserAPI {
  type Email = String
  type UserName = String
  type StreetAddress = String
  type APIKey = String

  def getUser(username: UserName, apiKey: APIKey): Option[User] =
    Some(User(username, "fred@mail.com"))
  def getStreetAddress(email: Email, apiKey: APIKey): Option[StreetAddress] =
    Some("1 Acacia Road")
}

object UserReaderAPI {
  import Kleisli._

  def getUser(username: UserName): ReaderT[Option, APIKey, User] = {
    kleisli { apiKey =>
      UserAPI.getUser(username, apiKey)
    }
  }
  def getStreetAddress(email: Email): ReaderT[Option, APIKey, StreetAddress] = {
    kleisli { apiKey =>
      UserAPI.getStreetAddress(email, apiKey)
    }
  }
}
