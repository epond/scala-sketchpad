package sketchpad.di.classic

object Runner extends App {
  val myController = MyController(new MyServiceImpl)
  println(myController.myMethod)
}
