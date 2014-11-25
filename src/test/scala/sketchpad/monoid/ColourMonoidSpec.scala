package sketchpad.monoid

import org.specs2.mutable.Specification
import scalaz._
import Scalaz._

class ColourMonoidSpec extends Specification {
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
