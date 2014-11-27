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
  val isBlack = mass == 0 || redBucket == 0 && greenBucket == 0 && blueBucket == 0
  val isWhite = mass > 0 && redBucket > 0 && redBucket == greenBucket && redBucket == blueBucket
}

object Colour {
  val red =   Colour(255, 0, 0, 1)
  val green = Colour(0, 255, 0, 1)
  val blue =  Colour(0, 0, 255, 1)
}

object AdditiveColourMonoid extends Monoid[Colour] {

  override def zero: Colour = Colour(0, 0, 0, 0)

  override def append(c1: Colour, c2: => Colour): Colour = {
    Colour(
      c1.redBucket+c2.redBucket,
      c1.greenBucket+c2.greenBucket,
      c1.blueBucket+c2.blueBucket,
      c1.mass+c2.mass
    )
  }
}
