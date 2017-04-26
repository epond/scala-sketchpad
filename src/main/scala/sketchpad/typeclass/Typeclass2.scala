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

object WeighableEvidence {
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
  def sumWeighable[S, T](a: S, b: T)(implicit evidence1: Weighable[S], evidence2: Weighable[T]): Int = {
    evidence1.weight(a) + evidence2.weight(b)
  }
}