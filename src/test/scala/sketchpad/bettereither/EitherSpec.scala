package sketchpad.bettereither

import sketchpad.bettereither.BiasedEither._
import org.specs2.mutable.Specification

class EitherSpec extends Specification {
  "An Either" should {
    "Be able to represent success" in {
      val successfulVal = 123
      val either: \/-[Int] = \/-(successfulVal)
      either.isRight must beTrue
      either.isLeft must beFalse
      (either match {
        case \/-(x) => x
        case _ => 0
      }) must beEqualTo(successfulVal)
    }

    "Be able to represent failure" in {
      val failureVal = "boom"
      val either = -\/(failureVal)
      either.isRight must beFalse
      either.isLeft must beTrue
      (either match {
        case -\/(x) => x
        case _ => ""
      }) must beEqualTo(failureVal)
    }

    "Provide a default map implementation that works on the right value" in {
      \/-(5).map(_ * 2) should beEqualTo(\/-(10))
    }

    "Provide a leftMap implementation that maps the left value" in {
      -\/(5).leftMap(_ * 2) should beEqualTo(-\/(10))
    }

    "Provide a flatMap implementation" in {
      \/-(5).flatMap(x => \/-(x * 2)) should beEqualTo(\/-(10))
    }

    "Behave correctly in a for comprehension" in {
      (for {
        a <- (\/-(2): String \/ Int)
        b <- (\/-(3): String \/ Int)
        c = 4
        if (b > a)
      } yield a * b * c) must beEqualTo(\/-(24))
    }
  }
}
