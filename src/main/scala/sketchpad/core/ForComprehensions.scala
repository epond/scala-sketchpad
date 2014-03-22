package sketchpad.core

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
    val mfmf = flatMapExpression(f1, f2, f3, test)
    // concatenate strings, perform addition, multiply length, ensure it is divisible by 2
    mfmf foreach(x => println("flatMap: " + x))

    // The same evaluation using a for comprehension
    val fc = forExpression(f1, f2, f3, test)
    // concatenate strings, perform addition, multiply length, ensure it is divisible by 2
    fc foreach(x => println("forComprehension: " + x))
  }

  def flatMapExpression(f1: Future[String], f2: Future[Int], f3: (String, Int) => Future[Int], test: Int => Boolean): Future[Int] = {
    f1.flatMap { a =>
      f2.flatMap { b =>
        f3(a, b).withFilter(test) map {
          c => c
        }
      }
    }
  }

  def forExpression(f1: Future[String], f2: Future[Int], f3: (String, Int) => Future[Int], test: Int => Boolean): Future[Int] = {
    for {
      a <- f1        // Future[String]
      b <- f2        // Future[Int]
      c <- f3(a, b)  // Future[Int]
      if test(c)
    } yield c
  }

}

object ForComprehensionsRunner extends App {
  ForComprehensions.main

  // Sleep so callbacks have a chance to complete
  Thread.sleep(100)
}

