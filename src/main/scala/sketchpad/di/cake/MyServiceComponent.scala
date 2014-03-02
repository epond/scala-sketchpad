package sketchpad.di.cake

trait MyServiceComponent {
  val myService: MyService

  class MyService {
    def doStuff: String = "default implementation"
  }
}
