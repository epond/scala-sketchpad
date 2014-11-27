package sketchpad.monoid

import org.specs2.mutable.Specification
import scalaz._
import Scalaz._

class ColourMonoidSpec extends Specification {

  implicit val colourMonoid = AdditiveColourMonoid

  "A Colour" should {
    "have zero component values when mass is zero" in {
      val colour = Colour(0, 10, 20, 0)
      colour.redComponent must beEqualTo(0)
      colour.greenComponent must beEqualTo(0)
      colour.blueComponent must beEqualTo(0)
    }

    "have component values equal to buckets when mass is 1" in {
      val colour = Colour(0, 10, 20, 1)
      colour.redComponent must beEqualTo(0)
      colour.greenComponent must beEqualTo(10)
      colour.blueComponent must beEqualTo(20)
    }

    "have component values equal to bucket divided by mass" in {
      val colour = Colour(0, 10, 20, 5)
      colour.redComponent must beEqualTo(0)
      colour.greenComponent must beEqualTo(2)
      colour.blueComponent must beEqualTo(4)
    }

    "recognise black" in {
      Colour.isBlack(Colour(0, 0, 0, 0)) must beTrue
      Colour.isBlack(Colour(0, 0, 0, 1)) must beTrue
      Colour.isBlack(Colour(10, 20, 30, 0)) must beTrue
      Colour.isBlack(Colour(10, 20, 30, 1)) must beFalse
    }

    "recognise white" in {
      Colour.isWhite(Colour(255, 255, 255, 1)) must beTrue
      Colour.isWhite(Colour(1, 1, 1, 1)) must beTrue
      Colour.isWhite(Colour(1, 1, 1, 0)) must beFalse
      Colour.isWhite(Colour(255, 254, 255, 1)) must beFalse
      Colour.isWhite(Colour(0, 0, 0, 0)) must beFalse
    }
  }

  "A ColourMonoid" should {
    "satisfy the identity monoid law" in {
      Monoid[Colour].zero |+| Colour.red   must beEqualTo(Colour.red)
      Monoid[Colour].zero |+| Colour.green must beEqualTo(Colour.green)
      Monoid[Colour].zero |+| Colour.blue  must beEqualTo(Colour.blue)
    }

    "satisfy the associativity law" in {
      Colour.red |+| Colour.red |+| Colour.blue must beEqualTo(
        Colour.red |+| Colour.blue |+| Colour.red
      )
    }
  }
}
