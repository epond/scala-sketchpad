package sketchpad.core

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
import scalaz._
import Scalaz._

object LayeredMonads extends App {

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
      ------------------------------------------------------------------------------ */
  val myList: List[Option[Int]] = List(Some(3), None, Some(4))
  val optionTList: List[Int] = OptionT(myList).getOrElse(0)
  println("List: " + myList)
  println("OptionT(List): " + optionTList)

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
