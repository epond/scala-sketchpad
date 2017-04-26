package sketchpad.typeclass

sealed trait Fuel
case object Fuel {
  case object Petrol extends Fuel
  case object Diesel extends Fuel
}

case class Car(fuel: Fuel)

case object Motorbike

trait Weighable[T] {
  def weight: Int
}

object Weighable {
  implicit object WeighableCar extends Weighable[Car] {
    val weight = 22
  }
  implicit object WeighableMotorbike extends Weighable[Motorbike.type] {
    val weight = 10
  }
}

object Scales {
  def sumWeighable[T](a: T, b: T)(implicit evidence: Weighable[T]): Int = {
    evidence.weight + evidence.weight
  }
}