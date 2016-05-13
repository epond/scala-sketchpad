package sketchpad.bettereither

import org.specs2.mutable.Specification

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class FutureOfEitherSpec extends Specification {
  "A composition of Future and Either" should {
    "have the properties of both the Future and Either effects throughout a for expression" in {
      val page: Future[PageRendering] = FuturesOfEither.renderPage("123", "Dave")
      val x: PageRendering = Await.result(page, Duration(1, SECONDS))
      x must beEqualTo(PageRendering("123Dave"))
    }
  }
}
