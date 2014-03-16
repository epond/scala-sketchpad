package sketchpad.di.scaldi

import scaldi.{Injector, Injectable}

class MyController(implicit inj: Injector) extends Injectable {
  val myService = inject [MyService]
  def myMethod = "Hello " + myService.doStuff
}
