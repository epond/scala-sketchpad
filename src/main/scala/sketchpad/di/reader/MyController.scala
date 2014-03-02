package sketchpad.di.reader

import scalaz._

object MyController {
  def myMethod: Reader[MyService, String] = Reader {
    myService => "Hello " + myService.doStuff
  }
}
