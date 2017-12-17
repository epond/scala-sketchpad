package sketchpad.core

import org.specs2.mutable.Specification
import scala.concurrent.{Await, Promise, Future}
import scala.concurrent.duration._

class FoldRetrySpec extends Specification {

  def succeedOnAttempt(requiredTries: Int): () => Future[String] = {
    var tryCount: Int = 0
    () => {
      tryCount += 1
      val p = Promise[String]()
      if (tryCount >= requiredTries) p.success("woohoo") else p.failure(new Exception("d'oh!"))
      p.future
    }
  }

  "A demonstration of fold" should {
    "given a block that succeeds on 3 attempts, succeed with max 3 attempts" in {
      val retryFuture = FoldRetry.retry(3)(succeedOnAttempt(3))
      Await.result(retryFuture, 1 seconds) must beEqualTo("woohoo")
    }

    "given a block that succeeds on 3 attempts, fail with max 2 attempts" in {
      val retryFuture = FoldRetry.retry(2)(succeedOnAttempt(3))
      Await.result(retryFuture, 1 seconds) must throwA[Exception]
    }

    "given a block that succeeds on 3 attempts, succeed with max 4 attempts" in {
      val retryFuture = FoldRetry.retry(4)(succeedOnAttempt(3))
      Await.result(retryFuture, 1 seconds) must beEqualTo("woohoo")
    }
  }
}