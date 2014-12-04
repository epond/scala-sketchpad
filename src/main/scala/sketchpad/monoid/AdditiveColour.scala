package sketchpad.monoid

import scalaz._

/**
 * Additive colour starts from black and applying all primary colours makes white.
 * The primary colours are Red, Green and Blue.
 */
case class AdditiveColour (redBucket: Long, greenBucket: Long, blueBucket: Long, mass: Long) {
  val isBlack = mass == 0 || redBucket == 0 && greenBucket == 0 && blueBucket == 0
  val isWhite = mass > 0 && redBucket > 0 && redBucket == greenBucket && redBucket == blueBucket
  val normalise = RGBColour(
    if (mass > 0) redBucket   / mass else 0,
    if (mass > 0) greenBucket / mass else 0,
    if (mass > 0) blueBucket  / mass else 0
  )
}

object AdditiveColour {
  def apply(colour: RGBColour): AdditiveColour = AdditiveColour(colour.red, colour.green, colour.blue, 1)
}

case class RGBColour(red: Long, green: Long, blue: Long)

object RGBColour {
  val red = RGBColour(255, 0, 0)
  val green = RGBColour(0, 255, 0)
  val blue = RGBColour(0, 0, 255)
}

/**
 * An analogy that can be applied to this monoid is that it represents a group of torches that
 * are emitting light of different colours and intensities.
 *
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
