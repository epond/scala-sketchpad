import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
val t1: Try[Future[String]] = Try(Future("Hello" + "World"))
//val t1: Try[Future[String]] = Try(throw new Exception)
val t2: Try[Future[Int]] = Try(Future(1 + 2))
//val t2: Try[Future[Int]] = Try(throw new Exception)
val t3: Try[(String, Int) => Future[Int]] = Try((a: String, b: Int) => Future(a.length * b))
//val t3: Try[(String, Int) => Future[Int]] = Try(throw new Exception)

val test = (a: Int) => a % 2 == 0

val wrappedResult: Try[Future[Int]] = for {
  f1 <- t1
  f2 <- t2
  f3 <- t3
} yield {
  for {
    a <- f1
    b <- f2
    c <- f3(a, b)
    if test(c)
  } yield c
}



val result = wrappedResult match {
  case Success(futureResult) => Await.result(futureResult, 1 second)
  case Failure(_) => -999
}

result




