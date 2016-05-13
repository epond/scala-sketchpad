package sketchpad.bettereither

object BiasedEither {
  sealed abstract class \/[+A, +B] extends Product with Serializable {

    /** Return `true` if this disjunction is left. */
    def isLeft: Boolean =
      this match {
        case -\/(_) => true
        case \/-(_) => false
      }

    /** Return `true` if this disjunction is right. */
    def isRight: Boolean =
      this match {
        case -\/(_) => false
        case \/-(_) => true
      }

    /** Map on the right of this disjunction. */
    def map[D](g: B => D): (A \/ D) =
      this match {
        case \/-(a) => \/-(g(a))
        case b@ -\/(_) => b
      }

    /** Filter on the right of this disjunction. */
    def withFilter[AA >: A](p: B => Boolean)(implicit Z: Zero[AA]): (AA \/ B) =
      this match {
        case -\/(_) => this
        case \/-(b) => if (p(b)) this else -\/(Z.zero)
      }

    /** Run the given function on the left value. */
    def leftMap[C](f: A => C): (C \/ B) =
      this match {
        case -\/(a) => -\/(f(a))
        case b@ \/-(_) => b
      }

    /** Bind through the right of this disjunction. */
    def flatMap[AA >: A, D](g: B => (AA \/ D)): (AA \/ D) =
      this match {
        case a@ -\/(_) => a
        case \/-(b) => g(b)
      }

    /** Express as an Option */
    val toOption =
      this match {
        case \/-(a) => Some(a)
        case -\/(_) => None
      }
  }

  /** A left disjunction
    *
    * Often used to represent the failure case of a result
    */
  final case class -\/[+A](a: A) extends (A \/ Nothing)

  /** A right disjunction
    *
    * Often used to represent the success case of a result
    */
  final case class \/-[+B](b: B) extends (Nothing \/ B)

  trait Zero[F] {
    /** The identity element for `append`. */
    def zero: F
  }

  object ZeroString extends Zero[String] {
    override def zero: String = ""
  }

  implicit val zeroString = ZeroString

  trait FromEitherToBiasedEither[L, R] {
    def asBiased: L \/ R
  }

  implicit def either2BiasedEither[L, R](adaptee: Either[L, R]): FromEitherToBiasedEither[L, R] = {
    new FromEitherToBiasedEither[L, R] {
      override def asBiased: L \/ R = adaptee match {
        case Right(r) => \/-(r)
        case Left(l) => -\/(l)
      }
    }
  }

  implicit class OptionOps[B](underlying: Option[B]){
    def toBiasedEither[A](ifNone: => A): A \/ B = underlying match {
      case Some(o) => \/-(o)
      case None => -\/(ifNone)
    }
  }
}