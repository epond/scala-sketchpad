package sketchpad.di.reader

import org.specs2.mutable.Specification

class MyComponentSpec extends Specification {
  "MyComponent" should {
    "prefix stuff with 'Hello'" in {

      val myComponentReader = MyComponent.myMethod

      val result = myComponentReader(new MyService{
        def doStuff: String = "test implementation"
      })

      result mustEqual("Hello test implementation")
    }
  }
}
