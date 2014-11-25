package sketchpad.monoid

import scalaz._

// Construct a ColourMonoid with cumulative component buckets and total mass
case class ColourMonoid (redBucket: Long, greenBucket: Long, blueBucket: Long, mass: Long) extends Monoid[ColourMonoid] {
  // Zero value has no mass to obey identity law
  override def zero: ColourMonoid = ColourMonoid(0, 0, 0, 0)

  // TODO Append adds components without wrapping, and increments mass by 1
  override def append(f1: ColourMonoid, f2: => ColourMonoid): ColourMonoid = ???

  // TODO Component getters divide component buckets by mass
}
