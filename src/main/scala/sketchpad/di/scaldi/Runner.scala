package sketchpad.di.scaldi

import scaldi.Injectable

object Runner extends App with Injectable {
  implicit val modules = new ConfigurationModule

  val myController = new MyController()

  println(myController.myMethod)
}
