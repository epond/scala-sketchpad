package sketchpad.monoid

import org.specs2.mutable.Specification
import scalaz._
import Scalaz._

class AdditiveColourMonoidSpec extends Specification {

  "An AdditiveColour" should {
    "have zero component values when mass is zero" in {
      val colour = AdditiveColour(0, 10, 20, 0)
      colour.redComponent must beEqualTo(0)
      colour.greenComponent must beEqualTo(0)
      colour.blueComponent must beEqualTo(0)
    }

    "have component values equal to buckets when mass is 1" in {
      val colour = AdditiveColour(0, 10, 20, 1)
      colour.redComponent must beEqualTo(0)
      colour.greenComponent must beEqualTo(10)
      colour.blueComponent must beEqualTo(20)
    }

    "have component values equal to bucket divided by mass" in {
      val colour = AdditiveColour(0, 10, 20, 5)
      colour.redComponent must beEqualTo(0)
      colour.greenComponent must beEqualTo(2)
      colour.blueComponent must beEqualTo(4)
    }

    "recognise black" in {
      AdditiveColour(0, 0, 0, 0).isBlack must beTrue
      AdditiveColour(0, 0, 0, 1).isBlack must beTrue
      AdditiveColour(10, 20, 30, 0).isBlack must beTrue
      AdditiveColour(10, 20, 30, 1).isBlack must beFalse
    }

    "recognise white" in {
      AdditiveColour(255, 255, 255, 1).isWhite must beTrue
      AdditiveColour(1, 1, 1, 1).isWhite must beTrue
      AdditiveColour(1, 1, 1, 0).isWhite must beFalse
      AdditiveColour(255, 254, 255, 1).isWhite must beFalse
      AdditiveColour(0, 0, 0, 0).isWhite must beFalse
    }
  }

  "An AdditiveColourMonoid" should {

    implicit val colourMonoid = AdditiveColourMonoid

    "satisfy the identity monoid law" in {
      Monoid[AdditiveColour].zero |+| AdditiveColour.red   must beEqualTo(AdditiveColour.red)
      Monoid[AdditiveColour].zero |+| AdditiveColour.green must beEqualTo(AdditiveColour.green)
      Monoid[AdditiveColour].zero |+| AdditiveColour.blue  must beEqualTo(AdditiveColour.blue)
    }

    "satisfy the associativity law" in {
      AdditiveColour.red |+| AdditiveColour.red |+| AdditiveColour.blue must beEqualTo(
        AdditiveColour.red |+| AdditiveColour.blue |+| AdditiveColour.red
      )
    }
  }
}
