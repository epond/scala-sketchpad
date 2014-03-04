package sketchpad.di.reader

import org.specs2.mutable.Specification

class MyControllerSpec extends Specification {
  "Reader Monad MyController" should {
    "prefix stuff with 'Hello'" in {
      val myControllerReader = MyController.myMethod
      val result = myControllerReader(new MyService{
        def doStuff: String = "test implementation"
      })
      result mustEqual("Hello test implementation")
    }
  }
}
