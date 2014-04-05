package sketchpad.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

object PlayJsonParsing extends App {
  val jsonString = """{
                     |  "name" : "Watership Down",
                     |  "location" : {
                     |    "lat" : 51.235685,
                     |    "long" : -1.309197
                     |  },
                     |  "residents" : [ {
                     |    "name" : "Fiver",
                     |    "age" : 4,
                     |    "role" : null
                     |  }, {
                     |    "name" : "Bigwig",
                     |    "age" : 6,
                     |    "role" : "Owsla"
                     |  } ]
                     |}""".stripMargin

  case class Location(lat: Double, long: Double)
  case class Resident(name: String, age: Int, role: Option[String])
  case class Place(name: String, location: Location, residents: Seq[Resident])

  implicit val locationReads: Reads[Location] = (
    (JsPath \ "lat").read[Double] and
      (JsPath \ "long").read[Double]
    )(Location.apply _)

  implicit val residentReads: Reads[Resident] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "age").read[Int] and
      (JsPath \ "role").readNullable[String]
    )(Resident.apply _)

  implicit val placeReads: Reads[Place] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "location").read[Location] and
      (JsPath \ "residents").read[Seq[Resident]]
    )(Place.apply _)

  val jsValue = Json.parse(jsonString)
  val placeResult: JsResult[Place] = jsValue.validate[Place]

  placeResult match {
    case s: JsSuccess[Place] => println(s.get)
    case _ => println("Error parsing")
  }
}
