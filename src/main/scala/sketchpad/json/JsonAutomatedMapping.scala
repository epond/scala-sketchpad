package sketchpad.json

import play.api.libs.json._

/**
  * This used to be called JSON macro inception but is now called
  * JSON automated mapping.
  * https://www.playframework.com/documentation/2.6.x/ScalaJsonAutomated
  */
object JsonAutomatedMapping {
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

  implicit val locationJsonFormat: Format[Location] = Json.format[Location]
  implicit val residentJsonFormat: Format[Resident] = Json.format[Resident]
  implicit val placeJsonFormat: Format[Place] = Json.format[Place]
}

object JsonAutomatedMappingRunner extends App {
  import JsonAutomatedMapping._

  val jsValue = Json.parse(jsonString)
  val placeResult: JsResult[Place] = jsValue.validate[Place]

  placeResult match {
    case s: JsSuccess[Place] => println(s.get)
    case e: JsError => println("Error when parsing: " + JsError.toJson(e).toString())
  }

  println(Json.toJson(
    Place("Watership Down",
      Location(51.235685, -1.309197),
      List(
        Resident("Fiver", 4, None),
        Resident("Bigwig", 6, Some("Owsla"))
      )
    )
  ))
}
