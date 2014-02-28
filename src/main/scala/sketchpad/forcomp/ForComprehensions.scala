package sketchpad.forcomp

import scala.concurrent._
import ExecutionContext.Implicits.global

object ForComprehensions {
  def main = {
    forComprehension
  }

  def forComprehension = {
    val f1 = Future("Hello" + "World")
    val f2 = Future(1 + 2)
    val f3 = (a: String, b: Int) => Future(a.length * b)
    val test = (a: Int) => a % 2 == 0

    // Evaluate using map, flatMap and filter
    val mfmf =
      f1.flatMap { a =>
        f2.flatMap { b =>
          f3(a, b).withFilter(test) map {
            c => c
          }
        }
      }
    // concatenate strings, perform addition, multiply length, ensure it is divisible by 2
    mfmf foreach(x => println("flatMap: " + x))

    // The same evaluation using a for comprehension
    val fc = for {
      a <- f1        // Future[String]
      b <- f2        // Future[Int]
      c <- f3(a, b)  // Future[Int]
      if test(c)
    } yield c
    // concatenate strings, perform addition, multiply length, ensure it is divisible by 2
    fc foreach(x => println("forComprehension: " + x))
  }
}

object ForComprehensionsRunner extends App {
  ForComprehensions.main

  // Sleep so callbacks have a chance to complete
  Thread.sleep(100)
}

