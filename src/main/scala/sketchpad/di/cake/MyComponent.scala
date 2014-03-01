package sketchpad.di.cake

trait MyComponent {
  def myService: MyService

  def myMethod = "Hello " + myService.doStuff
}
