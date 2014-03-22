package sketchpad.forcomp

import scala.concurrent.Future

object Fold {

  /**
   * retry demonstrates how fold can be used to repeatedly try something.
   * In this case it is repeatedly attempting to complete the future passed in
   * as the block parameter.
   *
   * retry(3) { block }
   * unfolds to
   * block fallbackTo { block fallbackTo { block fallbackTo { failed }}}
   *
   * @param noTimes Maximum number of attempts
   * @param block The Future that we want to repeatedly attempt
   * @tparam T The type of the Future in question
   * @return A Future with the required retry behaviour
   */
  def retry[T](noTimes: Int)(block: () => Future[T]): Future[T] = {
    val ns: Iterator[Int] = (1 to noTimes).iterator
    val attempts: Iterator[() => Future[T]] = ns.map(_ => block)
    val failed = () => Future.failed(new Exception)

    attempts.foldRight[() => Future[T]] (failed) (
      (block, acc) =>
        () => { block() fallbackTo { acc() } }
    )()
  }

}
