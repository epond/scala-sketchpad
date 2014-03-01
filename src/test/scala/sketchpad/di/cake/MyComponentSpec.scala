package sketchpad.di.cake

import org.specs2.mutable.Specification

class MyComponentSpec extends Specification {
   "MyComponent" should {
     "prefix stuff with 'Hello'" in {

       val myComponent = MyComponentTarget

       myComponent.myMethod mustEqual("Hello test implementation")
     }
   }
 }
