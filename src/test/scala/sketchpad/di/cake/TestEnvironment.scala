package sketchpad.di.cake

trait TestEnvironment extends MyControllerComponent with MyServiceComponent {
  val myService = new MyService {
    override def doStuff: String = "test implementation"
  }
  val myController = new MyController
}
