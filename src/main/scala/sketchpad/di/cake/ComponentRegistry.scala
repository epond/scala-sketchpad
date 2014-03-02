package sketchpad.di.cake

/**
 * Component wiring and instantiation defined in a single 'configuration' object
 */
object ComponentRegistry extends MyControllerComponent with MyServiceComponent {
  val myController = new MyController
  val myService = new MyService
}