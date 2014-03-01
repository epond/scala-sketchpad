package sketchpad.di.classic

object Runner extends App {
  val myComponent = MyComponent(new MyServiceImpl)

  println(myComponent.myMethod)
}
