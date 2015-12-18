package sketchpad.typeclass

object Group {

  trait Eq[T] {
    def myeq(a: T, b: T): Boolean
  }

  implicit object EqInt extends Eq[Int] {
    override def myeq(a: Int, b: Int): Boolean = a == b
  }

  def group[T](xs: List[T])(implicit ev: Eq[T]): List[List[T]] = {
    xs.foldLeft(Nil: List[List[T]]) {(acc: List[List[T]], item: T) =>
      if (acc.exists{x => ev.myeq(x.head, item)}) {
        acc.map{ x => {
          if (ev.myeq(x.head, item)) item::x else x
        }}
      } else {
        List(item) :: acc
      }
    }
  }
}
