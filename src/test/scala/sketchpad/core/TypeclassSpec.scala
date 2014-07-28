package sketchpad.core

import org.specs2.mutable.Specification

class TypeclassSpec extends Specification {
  "A NumberLike typeclass" should {
    "provide a member for Int implicitly" in {
      Statistics.mean(Vector[Int](1,2,3)) must beEqualTo(2)
    }
  }
}
