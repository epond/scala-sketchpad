package sketchpad.di.scaldi

import org.specs2.mutable.Specification
import scaldi.Module

class TestConfigurationModule extends Module {
  bind [MyService] to new MyService{
    def doStuff: String = "test implementation"
  }
}


class ScaldiMyControllerSpec extends Specification {
  "Scaldi MyController" should {
    "prefix stuff with 'Hello'" in {
      implicit val modules = new TestConfigurationModule
      new MyController().myMethod mustEqual("Hello test implementation")
    }
  }
}
