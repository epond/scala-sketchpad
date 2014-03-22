import scala.util.{Failure, Success, Try}

val a: Try[Int] = Try(2)
val b: Try[Int] = Try(3)
//val b: Try[Int] = Try(throw new Exception)

val resultTry: Try[Int] = for {
  x <- a
  y <- b
} yield x * y

val result = resultTry match {
  case Success(res) => res
  case Failure(_) => 0
}