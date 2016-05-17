package sketchpad.bettereither

import org.specs2.mutable.Specification
import sketchpad.bettereither.BiasedEither._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class FutureOfEitherSpec extends Specification {
  "render page" should {
    "contain article and contact list" in {
      pending
    }
    "contain article and empty contact list when contacts are empty" in {
      val lookup = DummyLookup(Article("123"), User("Dave"), List())
      val page: Future[PageRendering] = FuturesOfEither.renderPage("", "", lookup)
      val expectedRendering = "Article(123),List()"
      Await.result(page, Duration(1, SECONDS)) must beEqualTo(PageRendering(expectedRendering))
    }
    "contain article and empty contact list when user not found" in {
      pending
    }
  }
}

case class DummyLookup(article: Article, user: User, contacts: Seq[User]) extends Lookup {
  override def lookupArticle(id: String): Future[\/[String, Article]] = Future.successful(\/-(article))
  override def lookupUser(name: String): Future[\/[String, User]] = Future.successful(\/-(user))
  override def lookupContacts(user: \/[String, User]): Future[\/[String, Seq[User]]] = Future.successful(\/-(contacts))
}