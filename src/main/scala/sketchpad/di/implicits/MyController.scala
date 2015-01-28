package sketchpad.di.implicits

case class MyController(implicit myService: MyService) {
  def myMethod = "Hello " + myService.doStuff
}
