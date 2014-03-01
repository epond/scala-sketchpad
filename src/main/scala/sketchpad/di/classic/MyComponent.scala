package sketchpad.di.classic

case class MyComponent(myService: MyService) {
  def myMethod = "Hello " + myService.doStuff
}
