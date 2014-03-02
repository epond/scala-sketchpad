package sketchpad.di.cake

object ComponentRegistry extends MyControllerComponent with MyServiceComponent {
  val myService = new MyService
  val myController = new MyController
}