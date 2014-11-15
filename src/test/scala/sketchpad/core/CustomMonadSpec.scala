package sketchpad.core

import org.specs2.mutable.Specification

class CustomMonadSpec extends Specification {
  "A core Scala Option monad" should {
    "return a some value when some values are combined with maps" in {
      Some(1).flatMap(r1 => Some(2).map(r2 => r1+r2)) must beEqualTo(Some(3))
    }
    "return a some value when some values are combined in a for comprehension" in {
      (for(r1 <- Some(1); r2 <- Some(2)) yield r1+r2) must beEqualTo(Some(3))
    }

    "return none when some values and none are combined with maps" in {
      Some(1).flatMap(r1 => None.map(r2 => r1+r2)) must beNone
      (None:Option[Int]).flatMap(r1 => Some(2).map(r2 => r1+r2)) must beNone
    }
    "return none when some values and none are combined in a for comprehension" in {
      (for(r1 <- Some(1); r2 <- None) yield r1+r2) must beNone
      (for(r1 <- (None:Option[Int]); r2 <- Some(2)) yield r1+r2) must beNone
    }

    "return none when nones are combined with maps" in {
      None.flatMap(r1 => None.map(r1)) must beNone
    }
    "return none when nones are combined in a for comprehension" in {
      (for(r1 <- None; r2 <- None) yield r1) must beNone
    }
  }

  "My option monad" should {
    "return a has value when has values are combined with maps" in {
      Has(1).flatMap(r1 => Has(2).map(r2 => r1+r2)) must beEqualTo(Has(3))
    }
    "return a has value when has values are combined in a for comprehension" in {
      (for(r1 <- Has(1); r2 <- Has(2)) yield r1+r2) must beEqualTo(Has(3))
    }

    "return hasnot when has values and hasnot are combined with maps" in {
      Has(1).flatMap(r1 => HasNot.map(r2 => r1+r2)) must beEqualTo(HasNot)
      (HasNot:MyOption[Int]).flatMap(r1 => Has(2).map(r2 => r1+r2)) must beEqualTo(HasNot)
    }
    "return hasnot when has values and hasnot are combined in a for comprehension" in {
      (for(r1 <- Has(1); r2 <- HasNot) yield r1+r2) must beEqualTo(HasNot)
      (for(r1 <- (HasNot:MyOption[Int]); r2 <- Has(2)) yield r1+r2) must beEqualTo(HasNot)
    }

    "return hasnot when hasnots are combined with maps" in {
      HasNot.flatMap(r1 => HasNot.map(r1)) must beEqualTo(HasNot)
    }
    "return hasnot when hasnots are combined in a for comprehension" in {
      (for(r1 <- HasNot; r2 <- HasNot) yield r1) must beEqualTo(HasNot)
    }
  }

  "My Writer monad" should {
    "given one value then the log should be the value" in {
      val writer = Writer("a")
      writer.x must beEqualTo("a")
      writer.log must beEqualTo("a")
    }
    "given two combined values then the log should contain both values" in {
      val writer = (for(r1 <- Writer("a"); r2 <- Writer("b")) yield r1)
      writer.x must beEqualTo("a")
      writer.log must beEqualTo("a\nb")
    }
    "given three combined values then the log should contain all values" in {
      val writer = (for(r1 <- Writer("a"); r2 <- Writer("b"); r3 <- Writer("c")) yield r1)
      writer.x must beEqualTo("a")
      writer.log must beEqualTo("a\nb\nc")
    }
  }
}
