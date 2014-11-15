package sketchpad.core

object CustomMonad extends App {
  val result = for {
    r1 <- Has("Red")
    r2 <- Has("Green")
  } yield r1+r2

  println(result)

  val result2 =
    Has("Red")
      .flatMap(r1 => Has("Green")
      .map(r2=>r1+r2))

  println(result2)
}

/** MyOption mimics the core behaviour of Option */
sealed trait MyOption[+A] {
  def get: A
  def isEmpty = this match {
    case Has(x) => false
    case HasNot => true
  }
  def map[B](f: A => B): MyOption[B] = if (isEmpty) HasNot else Has(f(this.get))
  def flatMap[B](f: A => MyOption[B]): MyOption[B] = if (isEmpty) HasNot else f(this.get)
}
case class Has[A](x: A) extends MyOption[A] {
  def get: A = x
}
case object HasNot extends MyOption[Nothing] {
  def get = throw new IllegalArgumentException
}

/** Writer maintains a String log of all values concatenated */
case class Writer[A](x: A, log: String) {
  def map[B](f: A => B): Writer[B] = Writer(f(x), log)
  def flatMap[B](f: A => Writer[B]): Writer[B] = {
    val fx = f(x)
    Writer(fx.x, x.toString + '\n' + fx.log)
  }
}
object Writer {
  def apply[A](x: A): Writer[A] = Writer(x, x.toString)
}