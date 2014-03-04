package sketchpad.di.cake

import org.specs2.mutable.Specification

/**
 * MyController is being instantiated directly but its dependencies will be
 * satisfied according to TestEnvironment
 */
class MyControllerSpec extends Specification with TestEnvironment {
   "Cake MyController" should {
     "prefix stuff with 'Hello'" in {
       val myController = new MyController
       myController.myMethod mustEqual("Hello test implementation")
     }
   }
 }
