package sketchpad.di.subcut

object Runner extends App {
  implicit val bindingModule = ConfigurationModule

  val myController = new MyController()

  println(myController.myMethod)
}
