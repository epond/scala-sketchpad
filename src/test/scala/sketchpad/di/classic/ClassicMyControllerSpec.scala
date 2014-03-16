package sketchpad.di.classic

import org.specs2.mutable.Specification

class ClassicMyControllerSpec extends Specification {
  "Classic MyController" should {
    "prefix stuff with 'Hello'" in {
      val myController = MyController(new MyService{
        def doStuff: String = "test implementation"
      })
      myController.myMethod mustEqual("Hello test implementation")
    }
  }
}
