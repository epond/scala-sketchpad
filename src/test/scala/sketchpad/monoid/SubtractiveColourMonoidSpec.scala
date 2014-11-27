package sketchpad.monoid

import org.specs2.mutable.Specification
import scalaz._
import Scalaz._

class SubtractiveColourMonoidSpec extends Specification {

  "A SubtractiveColour" should {
    "have zero component values when mass is zero" in {
      val colour = SubtractiveColour(0, 10, 20, 0)
      colour.yellowComponent must beEqualTo(0)
      colour.magentaComponent must beEqualTo(0)
      colour.cyanComponent must beEqualTo(0)
    }
    "have component values equal to buckets when mass is 1" in {
      val colour = SubtractiveColour(0, 10, 20, 1)
      colour.yellowComponent must beEqualTo(0)
      colour.magentaComponent must beEqualTo(10)
      colour.cyanComponent must beEqualTo(20)
    }
    "have component values equal to bucket divided by mass" in {
      val colour = SubtractiveColour(0, 10, 20, 5)
      colour.yellowComponent must beEqualTo(0)
      colour.magentaComponent must beEqualTo(2)
      colour.cyanComponent must beEqualTo(4)
    }
    "recognise white" in {
      SubtractiveColour(0, 0, 0, 0).isWhite must beTrue
      SubtractiveColour(0, 0, 0, 1).isWhite must beTrue
      SubtractiveColour(10, 20, 30, 0).isWhite must beTrue
      SubtractiveColour(10, 20, 30, 1).isWhite must beFalse
      SubtractiveColour(1, 0, 0, 1).isWhite must beFalse
    }
    "recognise black" in {
      SubtractiveColour(255, 255, 255, 1).isBlack must beTrue
      SubtractiveColour(1, 1, 1, 1).isBlack must beTrue
      SubtractiveColour(1, 1, 1, 0).isBlack must beFalse
      SubtractiveColour(254, 255, 255, 1).isBlack must beFalse
      SubtractiveColour(0, 0, 0, 0).isBlack must beFalse
    }
  }

  "An SubtractiveColourMonoid" should {

    implicit val colourMonoid = SubtractiveColourMonoid

    "satisfy the identity monoid law" in {
      Monoid[SubtractiveColour].zero |+| SubtractiveColour.yellow  must beEqualTo(SubtractiveColour.yellow)
      Monoid[SubtractiveColour].zero |+| SubtractiveColour.magenta must beEqualTo(SubtractiveColour.magenta)
      Monoid[SubtractiveColour].zero |+| SubtractiveColour.cyan    must beEqualTo(SubtractiveColour.cyan)
    }

    "satisfy the associativity law" in {
      SubtractiveColour.yellow |+| SubtractiveColour.yellow |+| SubtractiveColour.cyan must beEqualTo(
        SubtractiveColour.yellow |+| SubtractiveColour.cyan |+| SubtractiveColour.yellow
      )
    }
  }
}
