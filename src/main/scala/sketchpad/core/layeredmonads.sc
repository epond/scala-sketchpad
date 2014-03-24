package sketchpad.core

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object layeredmonads_eclipse {
	val t1: Try[Future[String]] = Try(Future("Hello" + "World"))
                                                  //> t1  : scala.util.Try[scala.concurrent.Future[String]] = Success(scala.concur
                                                  //| rent.impl.Promise$DefaultPromise@79560ca4)
	//val t1: Try[Future[String]] = Try(throw new Exception)
	val t2: Try[Future[Int]] = Try(Future(1 + 2))
                                                  //> t2  : scala.util.Try[scala.concurrent.Future[Int]] = Success(scala.concurren
                                                  //| t.impl.Promise$DefaultPromise@573d59d4)
	//val t2: Try[Future[Int]] = Try(throw new Exception)
	val t3: Try[(String, Int) => Future[Int]] = Try((a: String, b: Int) => Future(a.length * b))
                                                  //> t3  : scala.util.Try[(String, Int) => scala.concurrent.Future[Int]] = Succes
                                                  //| s(<function2>)
	//val t3: Try[(String, Int) => Future[Int]] = Try(throw new Exception)
	
	val test = (a: Int) => a % 2 == 0         //> test  : Int => Boolean = <function1>
	
	val wrappedResult: Try[Future[Int]] = for {
	  f1 <- t1
	  f2 <- t2
	  f3 <- t3
	} yield {
	  for {
	    a <- f1
	    b <- f2
	    c <- f3(a, b)
	    if test(c)
	  } yield c                               //> wrappedResult  : scala.util.Try[scala.concurrent.Future[Int]] = Success(scal
                                                  //| a.concurrent.impl.Promise$DefaultPromise@7ffc9053)
	}
	
	
	
	val result = wrappedResult match {
	  case Success(futureResult) => Await.result(futureResult, 1 second)
	  case Failure(_) => -999
	}                                         //> result  : Int = 30
	
	result                                    //> res0: Int = 30
	
}