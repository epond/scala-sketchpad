package sketchpad.monoid

import scalaz._

/**
 * Subtractive colour starts from white and applying all primary colours makes black.
 * The primary colours are Yellow, Magenta and Cyan.
 */
case class SubtractiveColour(yellowBucket: Long, magentaBucket: Long, cyanBucket: Long, mass: Long) {
  val yellowComponent  = if (mass > 0) yellowBucket  / mass else 0
  val magentaComponent = if (mass > 0) magentaBucket / mass else 0
  val cyanComponent    = if (mass > 0) cyanBucket    / mass else 0
  val isBlack = mass > 0 && yellowBucket > 0 && yellowBucket == magentaBucket && yellowBucket == cyanBucket
  val isWhite = mass == 0 || yellowBucket == 0 && magentaBucket == 0 && cyanBucket == 0
}

object SubtractiveColour {
  val yellow  = SubtractiveColour(0, 255, 255, 1)
  val magenta = SubtractiveColour(255, 0, 255, 1)
  val cyan    = SubtractiveColour(255, 255, 0, 1)
}

object SubtractiveColourMonoid extends Monoid[SubtractiveColour] {

  override def zero: SubtractiveColour = SubtractiveColour(255, 255, 255, 0)

  override def append(c1: SubtractiveColour, c2: => SubtractiveColour): SubtractiveColour = c1 // ???
}
