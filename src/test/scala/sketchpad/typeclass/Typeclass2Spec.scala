package sketchpad.typeclass

import org.specs2.mutable.Specification
import sketchpad.typeclass.Fuel.{Diesel, Petrol}

class Typeclass2Spec extends Specification {
  "The Weighable typeclass" should {
    import sketchpad.typeclass.Weighable._
//    "find the weight of a Petrol Car" in {
//      Car(Petrol).weight must beEqualTo(20)
//    }
//    "find the weight of a Diesel Car" in {
//      Car(Diesel).weight must beEqualTo(22)
//    }
//    "find the weight of a Motorbike" in {
//      Motorbike.weight must beEqualTo(10)
//    }
    "find the combined weight of two Petrol Cars" in {
      Scales.sumWeighable(Car(Petrol), Car(Petrol)) must beEqualTo(40)
    }
    "find the combined weight of two different Cars" in {
      Scales.sumWeighable(Car(Petrol), Car(Diesel)) must beEqualTo(42)
    }
//    "find the combined weight of two Weighable things" in {
//      Scales.sumWeighable(Car(Diesel), Motorbike) must beEqualTo(32)
//    }
  }
}