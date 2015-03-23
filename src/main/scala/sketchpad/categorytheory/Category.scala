package sketchpad.categorytheory

object Category extends App {
  def id[A](x: A): A = x

  def composition[A, B, C](f: A => B, g: B => C): A => C = x => g(f(x)) // or g compose f
}
