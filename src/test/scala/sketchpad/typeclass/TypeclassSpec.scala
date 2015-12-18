package sketchpad.typeclass

import org.specs2.mutable.Specification

class TypeclassSpec extends Specification {
  "A NumberLike typeclass" should {
    "provide a member for Int implicitly" in {
      Statistics.mean(Vector[Int](1,2,3)) must beEqualTo(2)
    }
    "provide a member for String implicitly" in {
      Statistics.mean(Vector[String]("1","2","3")) must beEqualTo("2")
    }
  }

  "A list of EQables" should {
    "be groupable 1" in {
      Group.group(List[Int](1)) must beEqualTo(
        List(List(1)))
    }
    "be groupable 2" in {
      Group.group(List[Int](1,2)).sortBy(_.head) must beEqualTo(
        List(List(1), List(2)))
    }
    "be groupable 3" in {
      Group.group(List[Int](1,2,3,2)).sortBy(_.head) must beEqualTo(
        List(List(1), List(2,2), List(3)))
    }
  }
}
