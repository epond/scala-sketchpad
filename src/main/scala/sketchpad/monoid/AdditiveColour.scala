package sketchpad.monoid

import scalaz._

/**
 * Additive colour starts from black and applying all primary colours makes white.
 * The primary colours are Red, Green and Blue.
 */
case class AdditiveColour (redBucket: Long, greenBucket: Long, blueBucket: Long, mass: Long) {
  val redComponent   = if (mass > 0) redBucket   / mass else 0
  val greenComponent = if (mass > 0) greenBucket / mass else 0
  val blueComponent  = if (mass > 0) blueBucket  / mass else 0
  val isBlack = mass == 0 || redBucket == 0 && greenBucket == 0 && blueBucket == 0
  val isWhite = mass > 0 && redBucket > 0 && redBucket == greenBucket && redBucket == blueBucket
}

object AdditiveColour {
  val red =   AdditiveColour(255, 0, 0, 1)
  val green = AdditiveColour(0, 255, 0, 1)
  val blue =  AdditiveColour(0, 0, 255, 1)
}

/**
 * LYAHFGG says:
 * A monoid is when you have an associative binary function and a value which acts as an identity with respect
 * to that function.
 */
object AdditiveColourMonoid extends Monoid[AdditiveColour] {

  override def zero: AdditiveColour = AdditiveColour(0, 0, 0, 0)

  override def append(c1: AdditiveColour, c2: => AdditiveColour): AdditiveColour = {
    AdditiveColour(
      c1.redBucket+c2.redBucket,
      c1.greenBucket+c2.greenBucket,
      c1.blueBucket+c2.blueBucket,
      c1.mass+c2.mass
    )
  }
}
