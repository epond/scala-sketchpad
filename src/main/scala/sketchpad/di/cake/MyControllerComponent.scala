package sketchpad.di.cake

trait MyControllerComponent {
  this: MyServiceComponent =>
  val myController: MyController

  class MyController {
    def myMethod = "Hello " + myService.doStuff
  }
}
