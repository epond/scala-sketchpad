package sketchpad.di.classic

import org.specs2.mutable.Specification

class MyComponentSpec extends Specification {
  "MyComponent" should {
    "prefix stuff with 'Hello'" in {

      val myComponent = MyComponent(new MyService{
        def doStuff: String = "test implementation"
      })

      myComponent.myMethod mustEqual("Hello test implementation")
    }
  }
}
