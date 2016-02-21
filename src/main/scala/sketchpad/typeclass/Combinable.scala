package sketchpad.typeclass

// https://skillsmatter.com/skillscasts/6832-what-are-typeclasses-and-why-you-should-care

trait Combinable[T] {
  def combine(a: T, b: T): T
}

object Combinable {
  def apply[T](implicit c: Combinable[T]) = c

  trait CombinableOps[T] {
    def self: T
    def combinable: Combinable[T]
    def combine(other: T) = combinable.combine(self, other)
  }

  implicit object StringCombinable extends Combinable[String] {
    override def combine(a: String, b: String): String = a + b
  }

  object ops {
    implicit def toCombinableOps[T](t: T)(implicit c: Combinable[T]) = {
      new CombinableOps[T] {
        override def self: T = t
        override def combinable: Combinable[T] = c
      }
    }
  }
}