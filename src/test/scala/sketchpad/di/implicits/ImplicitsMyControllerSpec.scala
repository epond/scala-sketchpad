package sketchpad.di.implicits

import org.specs2.mutable.Specification

class ImplicitsMyControllerSpec extends Specification {
  "Implicits MyController" should {
    "prefix stuff with 'Hello'" in {
      implicit val implicitService = new MyService{
        def doStuff: String = "test implementation"
      }
      val myController = new MyController
      myController.myMethod mustEqual("Hello test implementation")
    }
  }
}
