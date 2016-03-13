package sketchpad.typeclass

import org.specs2.mutable.Specification

class CombinableSpec extends Specification {
  "The Combinable typeclass trait with manual boilerplate" should {
    "have its implementation implicitly provided" in {
      import Combinable.ops._
      "one" combine "two" combine "three" must beEqualTo("onetwothree")
    }
  }

  "The SimulacrumCombinable typeclass trait with macro boilerplate" should {
    "have its implementation implicitly provided" in {
      import SimulacrumCombinable.ops._
      implicit object StringSimulacrumCombinable extends SimulacrumCombinable[String] {
        override def combine(a: String, b: String): String = a + b
      }
      "one" combine "two" combine "three" must beEqualTo("onetwothree")
    }
  }
}
