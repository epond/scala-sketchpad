package sketchpad.core

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
import scalaz._
import Scalaz._

object LayeredMonads extends App {

  // see http://underscoreconsulting.com/blog/posts/2013/12/20/scalaz-monad-transformers.html
  // see http://noelwelsh.com/programming/2013/12/20/scalaz-monad-transformers/

  /** ------------------------------------------------------------------------------
      Monadic composition of Try[Future]
      If any of the input Try values fail then the aggregate Try fails.
      A nested for first unwraps the futures, then unwraps the underlying values.
      ------------------------------------------------------------------------------ */
  val try1: Try[Future[String]] = Try(Future("Hello" + "World"))
  //val try1: Try[Future[String]] = Try(throw new Exception)
  val try2: Try[Future[Int]] = Try(Future(1 + 2))
  //val try2: Try[Future[Int]] = Try(throw new Exception)
  val try3: Try[(String, Int) => Future[Int]] = Try((a: String, b: Int) => Future(a.length * b))
  //val try3: Try[(String, Int) => Future[Int]] = Try(throw new Exception)

  val condition = (a: Int) => a % 2 == 0

  val tryAggregate: Try[Future[Int]] = for {
    fut1 <- try1
    fut2 <- try2
    fut3 <- try3
  } yield {
    for {
      a <- fut1
      b <- fut2
      c <- fut3(a, b)
      if condition(c)
    } yield c
  }

  val tryResult = tryAggregate match {
    case Success(futureResult) => Await.result(futureResult, 1 second)
    case Failure(_) => -999
  }

  println("Try[Future] result: " + tryResult)



  /** ------------------------------------------------------------------------------
      Monadic composition of Reader[Config, Future] using a nested for comprehension.
      The for expression is the same as before but the resulting value is a
      function that takes a Config as input.
      ------------------------------------------------------------------------------ */
  case class Config(name: String)

  val r1: Reader[Config, Future[String]] =
    Reader(config => Future("Hello " + config.name))

  val r2: Reader[Config, Future[Int]] =
    Reader(config => Future(1 + 2))

  val r3: Reader[Config, (String, Int) => Future[Int]] =
    Reader(config => (a: String, b: Int) => Future(a.length * b))

  val readerAggregate: Reader[Config, Future[Int]] = for {
    fut1 <- r1
    fut2 <- r2
    fut3 <- r3
  } yield {
    for {
      a <- fut1
      b <- fut2
      c <- fut3(a, b)
      if condition(c)
    } yield c
  }

  val readerResult = readerAggregate(Config("Bobb"))

  println("readerAggregate nested for result: " + Await.result(readerResult, 1 second))

  /** ------------------------------------------------------------------------------
      Monad transformers.
      http://noelwelsh.com/programming/2013/12/20/scalaz-monad-transformers/
      ------------------------------------------------------------------------------ */
  // A simplistic first example. Combine a List with an Option.
  val optionTList: List[Int] = OptionT(List(Some(3), None, Some(4))).getOrElse(0)
  // optionTList.toString gives (3, 0, 4)
  println("optionTList: " + optionTList)

  // A second example. Combine a \/ with an Option.
  // We define a type here so that OptionT can perform type inferencing on the wrapped monad.
  type Error[+A] = \/[String, A]
  // Our resulting type wraps a \/[String, A] in an Option.
  type Result[A] = OptionT[Error, A]

  // The default way to construct a value is to use the point method.
  // point takes a value of any type and returns an applicative value with that value inside it
  // This first wraps 42 in a Some, and then in a right \/
  val result: Result[Int] = 42.point[Result]
  // We can't use point to construct a Result on a None because it is not on the 'happy path'.
  // Instead use the OptionT constructor:
  //val noneResult: Result[Int] = OptionT(none[Int].point[Error])
  // If we want to create a left \/ we go about it the same way:
  //val leftResult: Result[Int] = OptionT("Error message".left : Error[Option[Int]])
  val transformed =
    for {
      value <- result
    } yield value.toString
  println("transformed: " + transformed)

  /** ------------------------------------------------------------------------------
      Monadic composition of Reader[Config, Future] using a monad transformer.
      Something like this? http://typeclassopedia.bitbucket.org/#slide-115
      ------------------------------------------------------------------------------ */
//  val readerAggregate2: Reader[Config, Future[Int]] = for {
//    a <- Kleisli(r1)
//    b <- Kleisli(r2)
//    c <- Kleisli(r3)(a, b)
//    if condition(c)
//  } yield c
//
//  val readerResult2 = readerAggregate2(Config("Bobb"))
//
//  println("readerAggregate monad transformer result: " + Await.result(readerResult2, 1 second))
}
