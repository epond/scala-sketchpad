package sketchpad.typeclass

sealed trait Fuel
case object Fuel {
  case object Petrol extends Fuel
  case object Diesel extends Fuel
}

case class Car(fuel: Fuel)

case object Motorbike

trait Weighable[T] {
  def weight(x: T): Int
}

object Weighable {
  implicit object WeighableCar extends Weighable[Car] {
    def weight(c: Car) = c match {
      case Car(Fuel.Petrol) => 20
      case Car(Fuel.Diesel) => 22
    }
  }
  implicit object WeighableMotorbike extends Weighable[Motorbike.type] {
    def weight(m: Motorbike.type) = 10
  }
}

object Scales {
  def sumWeighable[T](a: T, b: T)(implicit evidence: Weighable[T]): Int = {
    evidence.weight(a) + evidence.weight(b)
  }
}