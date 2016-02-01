package sketchpad.http

import dispatch._, Defaults._

object Dispatch extends App {

  val http = Http()

  // Given a Location, build a Dispatch Request
  def weatherSvc(loc: Location): Req = {
    host("api.wunderground.com") / "apii" / "5a7c66db0ba0323a" /
      "conditions" / "q" / loc.state / (loc.city + ".xml")
  }

  //Given a Location, acquire either the weather xml or an error message
  //and log any errors
  def weatherXml(loc: Location): Future[Either[String, xml.Elem]] = {
    val res: Future[Either[Throwable, xml.Elem]] =
      http(weatherSvc(loc) OK as.xml.Elem).either
    for (exc <- res.left)
      yield exc match {
        case StatusCode(404) => {
          println(s"Log Error: service gave a 404")
          "Can't connect to weather service due to 404"
        }
        case StatusCode(statusCode) => {
          println(s"Log Error: service gave status code $statusCode")
          s"Can't connect to weather service due to $statusCode"
        }
        case _ => {
          println(s"Log Error: ${exc.getMessage}")
          "Can't connect to weather service due to: " + exc.getMessage
        }
      }
  }

  // Given the weather xml, extract either a temperature value or an error message
  def extractTemp(xml: scala.xml.Elem): Either[String,Float] = {
    val seq = for {
      elem <- xml \\ "temp_c"
    } yield elem.text.toFloat
    seq.headOption.toRight {
      "Temperature missing in service response"
    }
  }

  // Given a Location, acquire either the temperature value or an error message
  def temperature(loc: Location): Future[Either[String,Float]] = {
    for {
      xml <- weatherXml(loc).right
      t <- Future.successful(extractTemp(xml)).right
    } yield t
  }

  // The user needs to see either the temperature or a holding page.
  // Any errors must be logged (printed to console)
  temperature(Location("London", "United Kingdom")) foreach {
    _ match {
      case Right(temp) => println(s"Display: Temperature of London is $temp"); http.shutdown()
      case Left(error) => println("Display: Unable to get temperature"); http.shutdown()
    }
  }
}

case class Location(city: String, state: String)
