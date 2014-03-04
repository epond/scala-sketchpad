package sketchpad.di.subcut

import org.specs2.mutable.Specification

class MyControllerSpec extends Specification {
  "MyController" should {
    "prefix stuff with 'Hello'" in {
      ConfigurationModule.modifyBindings { implicit testModule =>
        testModule.bind [MyService] toSingle new MyService{
          def doStuff: String = "test implementation"
        }
        new MyController().myMethod mustEqual("Hello test implementation")
      }
    }
  }
}
