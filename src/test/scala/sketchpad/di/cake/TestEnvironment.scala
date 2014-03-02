package sketchpad.di.cake

/**
 * This test configuration replaces ComponentRegistry during unit tests.
 * It can define each component as a mock and is mixed into our test as a trait
 * so that the mocks will be wired into any declared component dependencies,
 * unless a component is instantiated directly.
 */
trait TestEnvironment extends MyControllerComponent with MyServiceComponent {
  val myController = new MyController {
    override def myMethod: String = "test implementation"
  }
  val myService = new MyService {
    override def doStuff: String = "test implementation"
  }
}
