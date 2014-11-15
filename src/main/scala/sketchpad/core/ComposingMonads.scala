package sketchpad.core

import scalaz._
import Scalaz._

/**
 * http://eed3si9n.com/learning-scalaz/Composing+monadic+functions.html
 */
object ComposingMonads extends App {
  // In Scalaz there’s a special wrapper for function of type A => M[B] called Kleisli
  val f = Kleisli { (x: Int) => (x + 1).some }
  val g = Kleisli { (x: Int) => (x * 100).some }

  // We can then compose the functions using <=<, which runs rhs first. Result is Some(401)
  println("<=<: " + (4.some >>= (f <=< g))) // could also have written this as 4.some flatMap (f <=< g)

  // There’s also >=>, which runs lhs first like f andThen g. Result is Some(500)
  println(">=>: " + (4.some >>= (f >=> g)))
}
