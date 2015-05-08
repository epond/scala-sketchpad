package sketchpad.core

import org.specs2.time.NoTimeConversions
import org.specs2.mutable.Specification
import scala.concurrent.{Await, Promise, Future}
import scala.concurrent.duration._
import scala.util.{Try, Failure, Success}

class FoldRetrySpec extends Specification with NoTimeConversions {

  def succeedOnAttempt(requiredTries: Int): () => Future[String] = {
    var tryCount: Int = 0
    () => {
      tryCount += 1
      val p = Promise[String]()
      if (tryCount >= requiredTries) p.success("woohoo") else p.failure(new Exception("d'oh!"))
      p.future
    }
  }

  "A demonstration of fold for Future retries" should {
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

  def succeedOnTryAttempt(requiredTries: Int): () => Try[String] = {
    var tryCount: Int = 0
    () => {
      tryCount += 1
      if (tryCount >= requiredTries) Success("woohoo") else Failure(new Exception("d'oh!"))
    }
  }

  "A demonstration of fold for Try retries" should {
    "given a block that succeeds on 3 attempts, succeed with max 3 attempts" in {
      val retryTry = FoldRetry.retryTry(3)(succeedOnTryAttempt(3))
      retryTry.get must beEqualTo("woohoo")
    }

    "given a block that succeeds on 3 attempts, fail with max 2 attempts" in {
      val retryTry = FoldRetry.retryTry(2)(succeedOnTryAttempt(3))
      retryTry.get must throwA[Exception]
//      retryTry match {
//        case Failure(ex) if (ex.getMessage.equals("d'oh!")) => ok
//        case _ => ko
//      }
    }

    "given a block that succeeds on 3 attempts, succeed with max 4 attempts" in {
      val retryTry = FoldRetry.retryTry(4)(succeedOnTryAttempt(3))
      retryTry.get must beEqualTo("woohoo")
    }
  }
}