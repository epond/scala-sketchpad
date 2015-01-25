package sketchpad.core

object PartialFunctions {

//  ===Haskell===
//  Prelude> let a = fmap (*) [1,2,3]
//  Prelude> :t a
//    a :: Num a => [a -> a]
//  Prelude> fmap (\f -> f 5) a
//  [5,10,15]

  val a = List(1,2,3) map ((_:Int)*(_:Int)).curried
  // a: List[Int => Int] = List(<function1>, <function1>, <function1>)
  val b = a map (_(5))
  // b: List[Int] = List(5, 10, 15)
}
