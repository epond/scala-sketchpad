package sketchpad.core

/**
 * http://www.scala-lang.org/api/2.10.4/index.html#scala.xml.package
 */
object ScalaXMLProcessing extends App {
  println(<a><b><d></d></b><b><e></e></b><c/></a> \ "b" \ "e")
}
