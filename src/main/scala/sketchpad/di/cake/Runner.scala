package sketchpad.di.cake

object Runner extends App {
  var myController = ComponentRegistry.myController
  println(myController.myMethod)
}
