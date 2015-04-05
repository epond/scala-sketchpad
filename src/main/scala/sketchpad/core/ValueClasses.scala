package sketchpad.core

object ValueClasses {
  implicit class RichInt(val self: Int) extends AnyVal {
    def toHexStr: String = java.lang.Integer.toHexString(self)
  }

  implicit class Piper[A](val x: A) extends AnyVal {
    def |>[B](f: A => B) = f(x)
  }
}