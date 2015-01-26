package sketchpad.core

// Inspired by http://eed3si9n.com/learning-scalaz/Applicative.html
object PartialFunctions {

  // res1 is a function that takes a function and applies it to 5
  val res1 = (_: Int => Int)(5)
  // The equivalent in Haskell: let res1 = (\f -> f 5)
  // res2 is a function that takes an Int and adds 1 to it
  val res2 = (x: Int) => x + 1
  // The equivalent in Haskell: let res2 = (\x -> x + 1)
  // res3 applies res1 to res2, the result being 6
  val res3 = res1(res2)
  // The equivalent in Haskell: let res3 = res1 res2

  // Let's map a function that takes two parameters over a functor
  val a = List(1,2,3) map ((_:Int)*(_:Int)).curried
  // a: List[Int => Int] = List(<function1>, <function1>, <function1>)
  // The equivalent in Haskell: let a = fmap (*) [1,2,3]
  val b = a map (_(5))
  // b: List[Int] = List(5, 10, 15)
  // The equivalent in Haskell: let b = fmap (\f -> f 5) a
}
