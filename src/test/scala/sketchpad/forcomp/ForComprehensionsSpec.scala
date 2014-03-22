package sketchpad.forcomp

import org.specs2.mutable.Specification
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import org.specs2.time.NoTimeConversions

class ForComprehensionsSpec extends Specification with NoTimeConversions {
  val f1 = Future("Hello" + "World")
  val f2 = Future(1 + 2)
  val f3 = (a: String, b: Int) => Future(a.length * b)
  val test = (a: Int) => a % 2 == 0

  "The flatmap expression" should {
    "evaluate to 30" in {
      // Evaluate using map, flatMap and filter
      val fmFuture = ForComprehensions.flatMapExpression(f1, f2, f3, test)
      // concatenate strings, perform addition, multiply length, ensure it is divisible by 2
      Await.result(fmFuture, 1 seconds) mustEqual(30)
    }
  }

  "The for-expression" should {
    "evaluate to 30" in {
      // The same evaluation using a for comprehension
      val fmFuture = ForComprehensions.forExpression(f1, f2, f3, test)
      Await.result(fmFuture, 1 seconds) mustEqual(30)
    }
  }
}
