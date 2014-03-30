package sketchpad.core

import scalaz._
import Scalaz._

/**
 * The point of Reader monad is to pass in the configuration information once and
 * everyone uses it without explicitly passing it around.
 *
 * http://eed3si9n.com/learning-scalaz-day10
 */
object ScalazReader extends App {
  def myName(step: String): Reader[String, String] = Reader {step + " I am " + _}

  def localExample: Reader[String, (String, String, String)] = for {
    a <- myName("First")
    b <- myName("Second") >=> Reader { _ + "dy"}
    c <- myName("Third")
  } yield (a, b, c)

  // This will output: (First I am Fred,Second I am Freddy,Third I am Fred)
  println(localExample("Fred"))


  // ReaderTOption monad combines Reader's ability to read from some configuration once,
  // and Option's ability to express failure.
  type ReaderTOption[A, B] = ReaderT[Option, A, B]
  object ReaderTOption extends KleisliFunctions with KleisliInstances {
    def apply[A, B](f: A => Option[B]): ReaderTOption[A, B] = kleisli(f)
  }

  def configure(key: String) = ReaderTOption[Map[String, String], String] {_.get(key)}

  def setupConnection = for {
    host <- configure("host")
    user <- configure("user")
    password <- configure("password")
  } yield (host, user, password)

  val goodConfig = Map(
    "host" -> "eed3si9n.com",
    "user" -> "sa",
    "password" -> "****"
  )

  println("setup with goodConfig: " + setupConnection(goodConfig))

  val badConfig = Map(
    "host" -> "example.com",
    "user" -> "sa"
  )

  println("setup with badConfig: " + setupConnection(badConfig))
}
