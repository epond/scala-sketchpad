package sketchpad.core

import scala.util.{Failure, Success, Try}

object try_eclipse {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val a: Try[Int] = Try(2)                        //> a  : scala.util.Try[Int] = Success(2)
	//val a: Try[Int] = Try(throw new Exception)
	val b: Try[Int] = Try(3)                  //> b  : scala.util.Try[Int] = Success(3)
	//val b: Try[Int] = Try(throw new Exception)
	
	val resultTry: Try[Int] = for {
	  x <- a
	  y <- b
	} yield x * y                             //> resultTry  : scala.util.Try[Int] = Success(6)
	
	val result = resultTry match {
	  case Success(res) => res
	  case Failure(_) => 0
	}                                         //> result  : Int = 6
}