package sketchpad.core

/**
 * Implicit definitions are those that the compiler is allowed to insert into a program
 * in order to fix any of its type errors. Whenever the compiler sees an X, but needs a
 * Y, it will look for an implicit function that converts X to Y.
 * Mark an available implicit conversion by bringing it into scope as a single identifier.
 */
object ImplicitConversions extends App {

  /** ------------------------------------------------------------------------------
      Implicit conversion to an expected type
      ------------------------------------------------------------------------------ */
  def printFruit(fruit: Fruit): Unit = println(fruit.name)
  // import the 'Preamble' object's implicit conversions as single identifiers
  import MyConversions.string2Fruit
  printFruit("apple")

  /** ------------------------------------------------------------------------------
      Implicit conversion of the receiver of a method call
      ------------------------------------------------------------------------------ */
  val apple = Fruit("apple")
  import MyConversions.fruitToSqueezable
  apple.squeeze
}

case class Fruit(name: String)
case class SqueezableFruit(fruit: Fruit) {
  def squeeze = println("Squeezing " + fruit.name)
}

// It's common to define implicit conversions in a 'Preamble' object like this
object MyConversions {
  implicit def string2Fruit(name: String): Fruit = Fruit("Fruit: " + name)
  implicit def fruitToSqueezable(fruit: Fruit): SqueezableFruit = SqueezableFruit(fruit)
}
