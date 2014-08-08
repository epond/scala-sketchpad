package sketchpad.core

/**
 * Ad-hoc polymorphism, also known as type classes. In Scala this is a trait with a type parameter.
 *
 * A typeclass is represented by a parameterised trait defining operations on member types.
 * A type T is a member of typeclass C[_] if there is a value of type C[T] available in implicit scope.
 * A context bound [T: C] in the type parameter list for a class or method asserts that T is a member of C[_].
 *
 * This allows greater decoupling than subtype polymorphism so for example Algebraic Data Types can
 * contain only the data relevant to them and do not need to implement behaviour themselves.
 *
 * See TypeclassSpec for how to use the typeclass
 *
 * http://typeclassopedia.bitbucket.org/
 * http://www.youtube.com/watch?v=IMGCDph1fNY
 * http://www.youtube.com/watch?v=sVMES4RZF-8
 */

object Math {
  trait NumberLike[T] {
    def plus(x: T, y: T): T
    def divide(x: T, y: Int): T
    def minus(x: T, y: T): T
  }

  object NumberLike {
    // NumberLikeInt is the member of the NumberLike typeclass for the Int type. It is made available in implicit scope.
    // Members of typeclasses are usually singleton objects
    implicit object NumberLikeInt extends NumberLike[Int] {
      def plus(x: Int, y: Int): Int = x + y
      def divide(x: Int, y: Int): Int = x / y
      def minus(x: Int, y: Int): Int = x - y
    }
  }
}

object Statistics {
  import sketchpad.core.Math.NumberLike
  def mean[T](xs: Vector[T])(implicit ev: NumberLike[T]): T =
    ev.divide(xs.reduce(ev.plus(_, _)), xs.size)
}
