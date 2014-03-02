package sketchpad.di.reader

object Runner extends App {
  val myControllerReader = MyController.myMethod
  println(myControllerReader(new MyServiceImpl))
}
