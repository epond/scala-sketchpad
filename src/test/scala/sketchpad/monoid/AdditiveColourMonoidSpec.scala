package sketchpad.monoid

import org.specs2.mutable.Specification
import scalaz._
import Scalaz._

class AdditiveColourMonoidSpec extends Specification {

  "An AdditiveColour" should {
    "have zero component values when mass is zero" in {
      val colour = AdditiveColour(0, 10, 20, 0)
      colour.normalise.red must beEqualTo(0)
      colour.normalise.green must beEqualTo(0)
      colour.normalise.blue must beEqualTo(0)
    }

    "have component values equal to buckets when mass is 1" in {
      val colour = AdditiveColour(0, 10, 20, 1)
      colour.normalise.red must beEqualTo(0)
      colour.normalise.green must beEqualTo(10)
      colour.normalise.blue must beEqualTo(20)
    }

    "have component values equal to bucket divided by mass" in {
      val colour = AdditiveColour(0, 10, 20, 5)
      colour.normalise.red must beEqualTo(0)
      colour.normalise.green must beEqualTo(2)
      colour.normalise.blue must beEqualTo(4)
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
      (Monoid[AdditiveColour].zero |+| AdditiveColour(RGBColour.red)).normalise   must beEqualTo(RGBColour.red)
      (Monoid[AdditiveColour].zero |+| AdditiveColour(RGBColour.green)).normalise must beEqualTo(RGBColour.green)
      (Monoid[AdditiveColour].zero |+| AdditiveColour(RGBColour.blue)).normalise  must beEqualTo(RGBColour.blue)
    }

    "satisfy the associativity law" in {
      (AdditiveColour(RGBColour.red) |+| AdditiveColour(RGBColour.red) |+| AdditiveColour(RGBColour.blue)).normalise must beEqualTo(
        (AdditiveColour(RGBColour.red) |+| AdditiveColour(RGBColour.blue) |+| AdditiveColour(RGBColour.red)).normalise
      )
    }
  }
}
