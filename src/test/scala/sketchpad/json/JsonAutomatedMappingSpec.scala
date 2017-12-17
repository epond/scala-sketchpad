package sketchpad.json

import org.specs2.mutable.Specification
import play.api.libs.json._
import sketchpad.json.JsonAutomatedMapping._

class JsonAutomatedMappingSpec extends Specification {

  val jsonString = """{"name":"Watership Down","location":{"lat":51.235685,"long":-1.309197},"residents":[{"name":"Fiver","age":4},{"name":"Bigwig","age":6,"role":"Owsla"}]}"""

  "A Play JSON Format instance" should {
    "Given JSON convert to case classes" in {
      val jsResult = Json.parse(jsonString).validate[Place]
      jsResult match {
        case s: JsSuccess[Place] => s.get must beEqualTo(
          Place("Watership Down",
            Location(51.235685, -1.309197),
            List(
              Resident("Fiver", 4, None),
              Resident("Bigwig", 6, Some("Owsla"))
            )
          )
        )
        case e: JsError => ko("did not parse")
      }
    }

    "Given case classes convert to JSON" in {
      val placeJson = Json.stringify(Json.toJson(
        Place("Watership Down",
          Location(51.235685, -1.309197),
          List(
            Resident("Fiver", 4, None),
            Resident("Bigwig", 6, Some("Owsla"))
          )
        )
      ))
      placeJson must beEqualTo(jsonString)
    }

    "Given JSON with an incorrect field name produce an error" in {
      val jsonWithIncorrectField = """{"name":"Watership Down","location":{"lat":51.235685,"long":-1.309197},"resident":[{"name":"Fiver","age":4},{"name":"Bigwig","age":6,"role":"Owsla"}]}"""
      val jsResult = Json.parse(jsonWithIncorrectField).validate[Place]
      jsResult match {
        case s: JsSuccess[Place] => ko("unexpected success")
        case e: JsError => ok("expected error")
      }
    }
  }
}
