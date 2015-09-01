package sketchpad.core

sealed trait Shape
case object Triangle extends Shape
case object Square extends Shape

object Reflection extends App {

  val stringToModuleClass: String => reflect.runtime.universe.ClassSymbol = fullyQualifiedPath => scala.reflect.runtime.currentMirror.staticClass(fullyQualifiedPath)
  val moduleClassToModule: reflect.runtime.universe.ClassSymbol => reflect.runtime.universe.Symbol = moduleClass => moduleClass.owner.typeSignature.member(moduleClass.name.toTermName)
  val moduleToInstance: reflect.runtime.universe.Symbol => Any = module => reflect.runtime.currentMirror.reflectModule(module.asModule).instance

  val stringToInstance = moduleToInstance.compose(moduleClassToModule.compose(stringToModuleClass))

  println(s"Instance of Square is ${stringToInstance("sketchpad.core.Square")}")

  val subclassInstances: String => Set[Any] = superclassFullName => scala.reflect.runtime.currentMirror.staticClass(superclassFullName).knownDirectSubclasses.map(_.fullName).map(stringToInstance)

  val allShapes: Set[Shape] = subclassInstances("sketchpad.core.Shape").asInstanceOf[Set[Shape]]

  println(s"Shape subclasses are $allShapes")
}
