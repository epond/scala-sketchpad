package sketchpad.core

import org.specs2.mutable.Specification

class ValueClassesSpec extends Specification {
  "A Value Class when defined as an implicit class" should {

    // http://docs.scala-lang.org/overviews/core/value-classes.html
    "facilitate extension methods without having to instantiate a new object" in {
      import ValueClasses._

      13.toHexStr must beEqualTo("d")
    }

    // http://stackoverflow.com/a/29380677/20371
    "provide a convenient way to define a pipe operator for composing functions" in {
      import ValueClasses._
      def f1(s: String) = s.length
      def f2(x: Int) = x * 2

      "abc" |> f1 |> f2 must beEqualTo(f2(f1("abc")))
    }
  }
}
