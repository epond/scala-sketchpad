package sketchpad.bettereither

import sketchpad.bettereither.BiasedEither._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// https://medium.com/coding-with-clarity/practical-monads-dealing-with-futures-of-options-8260800712f8
object FuturesOfEither {
  def renderPage(articleId: String, userName: String, lookup: Lookup): Future[PageRendering] = {
    for {
      articleE <- lookup.lookupArticle(articleId)
      userE <- lookup.lookupUser(userName)
      contactsE <- lookupContacts(userE, lookup)
    } yield {
      val contacts: Seq[User] = contactsE match {
        case \/-(contacts) => contacts
        case -\/(_) => Nil
      }
      articleE match {
        case \/-(article) => {
          PageRendering(s"$article,$contacts")
        }
        case -\/(msg) => PageRendering(s"Error: $msg")
      }
    }
  }

  def lookupContacts(userE: String \/ User, lookup: Lookup): Future[String \/ Seq[User]] = userE match {
    case \/-(user) => lookup.lookupContacts(user)
    case -\/(_) => Future.successful(\/-(List()))
  }
}

case class User(name: String)
case class Article(id: String)
case class PageRendering(content: String)

trait Lookup {
  def lookupArticle(id: String): Future[String \/ Article]
  def lookupUser(name: String): Future[String \/ User]
  def lookupContacts(user: User): Future[String \/ Seq[User]]
}