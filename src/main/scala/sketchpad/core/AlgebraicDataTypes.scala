package sketchpad.core

/**
 * http://tech.esper.com/2014/07/30/algebraic-data-types/
 */
object AlgebraicDataTypes extends App {
  println(Plus(Number(1), Minus(Number(3), Number(2))))
}

// Expression is a Variant, meaning it can be any of multiple possibilities.
sealed trait Expression
case class Number (value: Int) extends Expression
// Plus is a Product, meaning it contains multiple values
case class Plus (lhs: Expression, rhs: Expression) extends Expression
case class Minus (lhs: Expression, rhs: Expression) extends Expression
// If a type possibility takes no parameters then use a case object
case object Infinity extends Expression