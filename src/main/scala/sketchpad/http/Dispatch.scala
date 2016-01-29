package sketchpad.http

import dispatch._, Defaults._

object Dispatch extends App {

  def weatherSvc(loc: Location): Req = {
    host("api.wunderground.com") / "api" / "5a7c66db0ba0323a" /
      "conditions" / "q" / loc.state / (loc.city + ".xml")
  }

  def weatherXml(loc: Location): Future[Either[String, xml.Elem]] = {
    val res: Future[Either[Throwable, xml.Elem]] =
      Http(weatherSvc(loc) OK as.xml.Elem).either
    for (exc <- res.left)
      yield "Can't connect to weather service: \n" +
        exc.getMessage
  }

  def extractTemp(xml: scala.xml.Elem): Either[String,Float] = {
    val seq = for {
      elem <- xml \\ "temp_c"
    } yield elem.text.toFloat
    seq.headOption.toRight {
      "Temperature missing in service response"
    }
  }

  def temperature(loc: Location): Future[Either[String,Float]] = {
    for {
      xml <- weatherXml(loc).right
      t <- Future.successful(extractTemp(xml)).right
    } yield t
  }

  temperature(Location("London", "United Kingdom")) foreach { _ match {
    case Right(temp) => println(temp)
    case _ =>
    }
  }
}

case class Location(city: String, state: String)
