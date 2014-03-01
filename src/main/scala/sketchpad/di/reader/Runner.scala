package sketchpad.di.reader

object Runner extends App {
  val myComponentReader = MyComponent.myMethod

  println(myComponentReader(new MyServiceImpl))
}
