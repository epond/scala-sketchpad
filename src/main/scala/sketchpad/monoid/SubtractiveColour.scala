package sketchpad.monoid

import scalaz._

/**
 * Subtractive colour starts from white and applying all primary colours makes black.
 * The primary colours are Cyan, Magenta and Yellow
 */
case class SubtractiveColour(cyanBucket: Long, magentaBucket: Long, yellowBucket: Long, mass: Long) {
  val isBlack = mass > 0 && yellowBucket > 0 && yellowBucket == magentaBucket && yellowBucket == cyanBucket
  val isWhite = mass == 0 || yellowBucket == 0 && magentaBucket == 0 && cyanBucket == 0
  val normalise = CMYColour(
    if (mass > 0) cyanBucket    / mass else 0,
    if (mass > 0) magentaBucket / mass else 0,
    if (mass > 0) yellowBucket  / mass else 0
  )
}

object SubtractiveColour {
  def apply(colour: CMYColour): SubtractiveColour = SubtractiveColour(colour.cyan, colour.magenta, colour.yellow, 1)
}

case class CMYColour(cyan: Long, magenta: Long, yellow: Long)

object CMYColour {
  val cyan    = CMYColour(0, 255, 255)
  val magenta = CMYColour(255, 0, 255)
  val yellow  = CMYColour(255, 255, 0)
}

/**
 * An analogy that can be applied to this monoid is that it represents a group of filters of
 * varying colour and opaqueness.
 */
object SubtractiveColourMonoid extends Monoid[SubtractiveColour] {

  override def zero: SubtractiveColour = SubtractiveColour(255, 255, 255, 0)

  override def append(c1: SubtractiveColour, c2: => SubtractiveColour): SubtractiveColour = c1 // ???
}
