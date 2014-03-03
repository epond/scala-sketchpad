package sketchpad.di.subcut

import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

case class MyController()(implicit val bindingModule: BindingModule) extends Injectable {
  val myService = injectOptional [MyService] getOrElse { new MyServiceImpl() }
  def myMethod = "Hello " + myService.doStuff
}
