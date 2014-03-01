package sketchpad.di.cake

trait MyServiceMock extends MyService {
  def doStuff: String = "test implementation"
}

object MyComponentTarget extends MyComponent with MyServiceMock