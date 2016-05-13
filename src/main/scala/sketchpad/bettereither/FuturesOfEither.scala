package sketchpad.bettereither

import sketchpad.bettereither.BiasedEither._

import scala.concurrent.Future

// https://medium.com/coding-with-clarity/practical-monads-dealing-with-futures-of-options-8260800712f8
object FuturesOfEither {
  private def lookupArticle(id: String):  Future[String \/ Article] = ???
  private def lookupUser(name: String):   Future[String \/ User] = ???
  private def lookupContacts(user: User): Future[String \/ Seq[User]] = ???

  def renderPage(articleId: String, userName: String): Future[PageRendering] = {
    ???
  }
}

case class User(name: String)
case class Article(id: String)
case class PageRendering(content: String)