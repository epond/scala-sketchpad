package sketchpad.typeclass

import org.specs2.mutable.Specification
import Fuel.{Diesel, Petrol}

class Typeclass2Spec extends Specification {
  "The Weighable typeclass when used explicitly" should {
    "find the weight of a Petrol Car" in {
      WeighableEvidence.WeighableCar.weight(Car(Petrol)) must beEqualTo(20)
    }
    "find the weight of a Diesel Car" in {
      WeighableEvidence.WeighableCar.weight(Car(Diesel)) must beEqualTo(22)
    }
    "find the weight of a Motorbike" in {
      WeighableEvidence.WeighableMotorbike.weight(Motorbike) must beEqualTo(10)
    }
    "find the combined weight of two Petrol Cars" in {
      Scales.sumWeighable(Car(Petrol), Car(Petrol))(WeighableEvidence.WeighableCar, WeighableEvidence.WeighableCar) must beEqualTo(40)
    }
    "find the combined weight of two different Cars" in {
      Scales.sumWeighable(Car(Petrol), Car(Diesel))(WeighableEvidence.WeighableCar, WeighableEvidence.WeighableCar) must beEqualTo(42)
    }
    "find the combined weight of two Weighable things" in {
      Scales.sumWeighable(Car(Diesel), Motorbike)(WeighableEvidence.WeighableCar, WeighableEvidence.WeighableMotorbike) must beEqualTo(32)
    }
  }
  "The Weighable typeclass when used implicitly" should {
    import WeighableEvidence._
    "find the combined weight of two Petrol Cars" in {
      Scales.sumWeighable(Car(Petrol), Car(Petrol)) must beEqualTo(40)
    }
    "find the combined weight of two different Cars" in {
      Scales.sumWeighable(Car(Petrol), Car(Diesel)) must beEqualTo(42)
    }
    "find the combined weight of two Weighable things" in {
      Scales.sumWeighable(Car(Diesel), Motorbike) must beEqualTo(32)
    }
  }
}