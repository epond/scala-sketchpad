package sketchpad.json

import play.api.libs.json._

object PlayJsonMacroInception extends App {
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

  implicit val locationReads: Reads[Location] = Json.reads[Location]
  implicit val residentReads: Reads[Resident] = Json.reads[Resident]
  implicit val placeReads: Reads[Place] = Json.reads[Place]

  val jsValue = Json.parse(jsonString)
  val placeResult: JsResult[Place] = jsValue.validate[Place]

  placeResult match {
    case s: JsSuccess[Place] => println(s.get)
    case e: JsError => println("Error when parsing: " + JsError.toFlatJson(e).toString())
  }
}
