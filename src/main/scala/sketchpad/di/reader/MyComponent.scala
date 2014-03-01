package sketchpad.di.reader

import scalaz._

object MyComponent {
  def myMethod: Reader[MyService, String] = Reader {
    myService => "Hello " + myService.doStuff
  }
}
