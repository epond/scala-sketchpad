package sketchpad.di.subcut

object Runner extends App {
  val myController = MyController(new MyServiceImpl)
  println(myController.myMethod)
}
