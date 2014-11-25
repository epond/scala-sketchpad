package sketchpad.monoid

import scalaz._

/**
 * Construct a ColourMonoid with cumulative component buckets and total mass
 *
 * LYAHFGG says:
 * A monoid is when you have an associative binary function and a value which acts as an identity with respect to that function.
 */

case class Colour (redBucket: Long, greenBucket: Long, blueBucket: Long, mass: Long) {
  val redComponent   = if (mass > 0) redBucket   / mass else 0
  val greenComponent = if (mass > 0) greenBucket / mass else 0
  val blueComponent  = if (mass > 0) blueBucket  / mass else 0
}

object Colour {
  val red =   Colour(255, 0, 0, 1)
  val green = Colour(0, 255, 0, 1)
  val blue =  Colour(0, 0, 255, 1)
}

object AdditiveColourMonoid extends Monoid[Colour] {
  // Zero value has no mass to obey identity law
  override def zero: Colour = Colour(0, 0, 0, 0)

  // TODO Append adds components without wrapping, and increments mass by 1
  override def append(colour1: Colour, colour2: => Colour): Colour = colour1
}
