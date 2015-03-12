package sketchpad.core

import scala.annotation.tailrec

object TailRecursion {
  /**
   * factorialNotTailrec is not tail-recursive because the recursive call is
   * not the last thing in the method body
   */
  //@tailrec
  final def factorialNotTailrec(n: Int): Int = n match {
    case 0 => 1
    case _ => n * factorialNotTailrec(n - 1)
  }

  /**
   * factorialTailrec is tail-recursive because the recursive call is the
   * last thing in the method body
   */
  @tailrec
  final def factorialTailrec(n: Int, accumulator: Int): Int = n match {
    case 0 => accumulator
    case _ => factorialTailrec(n - 1, accumulator * n)
  }
}
