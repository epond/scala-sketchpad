package sketchpad.core

import org.specs2.mutable.Specification

class AlgebraicDataTypesSpec extends Specification {

  "An OrderProcessor" should {
    "filter by order id" in {
      val instrs = List(
        Order(1, 10.23, 5),
        Order(2, 4.97, 3),
        Cancel(2)
      )

      val processedOrders = OrderProcessor.filterByOid(instrs, 2)
      processedOrders.size must beEqualTo(2)
      processedOrders.forall(idEquals(2)) must beTrue
    }
  }

  def idEquals(id: Int): (Instruction) => Boolean = instr => instr match {
    case Order(i,_,_) => i == id
    case Cancel(i) => i == id
  }
}
