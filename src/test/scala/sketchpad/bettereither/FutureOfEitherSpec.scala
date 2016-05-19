package sketchpad.bettereither

import org.specs2.mutable.Specification
import sketchpad.bettereither.BiasedEither._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class FutureOfEitherSpec extends Specification {
  "render page" should {
    "contain article and contact list" in {
      val lookup = DummyLookup(\/-(Article("123")), \/-(User("Dave")), \/-(List(User("Rod"), User("Jane"))))
      val page: Future[PageRendering] = FuturesOfEither.renderPage("", "", lookup)
      val expectedRendering = "Article(123),List(User(Rod), User(Jane))"
      Await.result(page, Duration(1, SECONDS)) must beEqualTo(PageRendering(expectedRendering))
    }
    "contain article and empty contact list when contacts are empty" in {
      val lookup = DummyLookup(\/-(Article("123")), \/-(User("Dave")), \/-(List()))
      val page: Future[PageRendering] = FuturesOfEither.renderPage("", "", lookup)
      val expectedRendering = "Article(123),List()"
      Await.result(page, Duration(1, SECONDS)) must beEqualTo(PageRendering(expectedRendering))
    }
    "contain article and empty contact list when user lookup fails" in {
      val lookup = DummyLookup(\/-(Article("123")), -\/("user fail"), \/-(List(User("Freddie"))))
      val page: Future[PageRendering] = FuturesOfEither.renderPage("", "", lookup)
      val expectedRendering = "Article(123),List()"
      Await.result(page, Duration(1, SECONDS)) must beEqualTo(PageRendering(expectedRendering))
    }
    "contain error when article lookup fails" in {
      val lookup = DummyLookup(-\/("article fail"), \/-(User("Dave")), \/-(List(User("Freddie"))))
      val page: Future[PageRendering] = FuturesOfEither.renderPage("", "", lookup)
      val expectedRendering = "Error: article fail"
      Await.result(page, Duration(1, SECONDS)) must beEqualTo(PageRendering(expectedRendering))
    }
  }
}

case class DummyLookup(article: String \/ Article,
                       user: String \/ User,
                       contacts: String \/ Seq[User]) extends Lookup {
  override def lookupArticle(id: String): Future[\/[String, Article]] = Future.successful(article)
  override def lookupUser(name: String): Future[\/[String, User]] = Future.successful(user)
  override def lookupContacts(user: User): Future[\/[String, Seq[User]]] = Future.successful(contacts)
}