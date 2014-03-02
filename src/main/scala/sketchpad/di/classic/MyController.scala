package sketchpad.di.classic

case class MyController(myService: MyService) {
  def myMethod = "Hello " + myService.doStuff
}
