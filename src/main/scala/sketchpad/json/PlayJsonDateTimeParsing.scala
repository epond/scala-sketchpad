package sketchpad.json

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import play.api.data.validation.ValidationError

object PlayJsonDateTimeParsing extends App {
  val jsonString = """{
                     |  "field1" : "2014-04-03T17:40:23.386+01:00",
                     |  "field2" : "2014-04-03"
                     |}""".stripMargin

  case class MyDTO(field1: DateTime, field2: DateTime)

  implicit val placeReads: Reads[MyDTO] = (
    (JsPath \ "field1").read[DateTime](DateConversion.jodaISODateReads) and
    (JsPath \ "field2").read[DateTime]
  )(MyDTO.apply _)

  val jsValue = Json.parse(jsonString)
  val placeResult: JsResult[MyDTO] = jsValue.validate[MyDTO]

  placeResult match {
    case s: JsSuccess[MyDTO] => println(s.get)
    case e: JsError => println("Error when parsing: " + JsError.toFlatJson(e).toString())
  }
}

object DateConversion {
  implicit val jodaISODateReads: Reads[DateTime] = new Reads[DateTime] {
    val df = org.joda.time.format.ISODateTimeFormat.dateTime()

    def reads(json: JsValue): JsResult[DateTime] = json match {
      case JsNumber(d) => JsSuccess(new DateTime(d.toLong))
      case JsString(s) => parseDate(s) match {
        case Some(d) => JsSuccess(d)
        case None => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.jodadate.format", "ISODateTimeFormat"))))
      }
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("validate.error.expected.date"))))
    }

    private def parseDate(input: String): Option[DateTime] =
      scala.util.control.Exception.allCatch[DateTime] opt (DateTime.parse(input, df))
  }
}
