package sketchpad.categorytheory

import org.specs2.mutable.Specification

class CategorySpec extends Specification {
  "My Category Theory exercise code" should {
    "define identity function" in {
      val exampleList = List(1,2,3)
      exampleList.map(Category.id) should beEqualTo(exampleList)
    }

    "define composition" in {
      def f(x: Int) = x * 2
      def g(x: Int) = x + 1
      def h = Category.composition(f, g)
      h(2) should beEqualTo(5)
    }

    "define composition such that it respects identity" in {
      val exampleList = List(1,2,3)
      def f(x: Int): Int = x * 2
      exampleList.map(Category.composition(f, Category.id[Int])) should beEqualTo(exampleList.map(f))
      exampleList.map(Category.composition(Category.id, f)) should beEqualTo(exampleList.map(f))
    }
  }
}
