package sketchpad.di.reader

import scalaz._

/**
 * To use the reader monad for dependency injection, we define a function whose input
 * is the dependency and wrap the function in a scalaz.Reader to give us map and flatMap.
 * The return type of myMethod is a decorated Myservice => String that we can use in
 * for comprehensions.
 * The actual injection of the dependency (MyService) is deferred up to a higher layer.
 *
 * The point of Reader monad is to pass in the configuration information once and everyone
 * uses it without explicitly passing it around.
 */
object MyController {
  def myMethod: Reader[MyService, String] = Reader {
    myService => "Hello " + myService.doStuff
  }
}
