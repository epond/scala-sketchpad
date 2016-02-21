package sketchpad.typeclass

import org.specs2.mutable.Specification

class CombinableSpec extends Specification {
  "The Combinable typeclass trait" should {
    "have its implementation implicitly provided" in {
      import Combinable.ops._
      "one" combine "two" combine "three" must beEqualTo("onetwothree")
    }
  }
}
