package sketchpad.di.cake

import org.specs2.mutable.Specification

class MyControllerSpec extends Specification with TestEnvironment {
   "MyController" should {
     "prefix stuff with 'Hello'" in {
       val myController = new MyController
       myController.myMethod mustEqual("Hello test implementation")
     }
   }
 }
