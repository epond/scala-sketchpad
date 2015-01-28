package sketchpad.di.implicits

object Runner extends App {
  implicit val implicitService = new MyServiceImpl
  val myController = new MyController
  println(myController.myMethod)
}
