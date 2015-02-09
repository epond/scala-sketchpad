package sketchpad.core

/**
 * Algebraic Data Types allow us to encode our proof obligations into the type system.
 * A type system of this kind consists of the following:
 *
 * Two kinds of types: products and sums
 * A match statement: data structure driven case analysis with static checks preventing:
 *   missed cases - ensures case analysis is exhaustive over all cases
 *   redundant cases - ensures there are no cases that will never be reached
 *   impossible cases - ensures each case is possible with the given type
 *
 * This example is from the Jane Street Capital video about how they use OCaml.
 * https://blogs.janestreet.com/caml-trading-talk-at-cmu/
 *
 * See also http://tech.esper.com/2014/07/30/algebraic-data-types/
 */
object AlgebraicDataTypes extends App {

  val instrs = List(
    Order(1,10.23,5),
    Order(2,4.97,3),
    Cancel(2)
  )

  println(OrderProcessor.filterByOid(instrs, 2))
}

object OrderProcessor {
  // Here is an example of using a match statement to process our type while enforcing the inherent static checks
  def filterByOid(instructions: Seq[Instruction], oid: Int): Seq[Instruction] = {
    instructions.filter(_ match {
      case Order(id,_,_) => id == oid
      case Cancel(xid) => xid == oid
      // Using a default case like below will remove the benefit of the static type checks
      // case _ => false
    })
  }
}

// Instruction is a Sum type (or Variant) meaning it can be any of multiple possibilities
sealed trait Instruction
// Order and Cancel are Product types meaning they contain multiple values
/** Place a transaction order */
case class Order (id: Int, price: BigDecimal, size: Int) extends Instruction
// Commenting out either Order or Cancel will make the compiler flag up an impossible case
/** Cancel a transaction order */
case class Cancel (xid: Int) extends Instruction

// Uncommenting CancelReplace will make the compiler flag up a missed case
/** Cancel a transaction order and replace it using the provided details */
//case class CancelReplace(xrid: Int, newPrice: BigDecimal, newSize: Int) extends Instruction