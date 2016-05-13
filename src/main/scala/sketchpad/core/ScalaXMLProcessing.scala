package sketchpad.core

import scala.xml.Node

/**
 * http://www.scala-lang.org/api/2.10.4/index.html#scala.xml.package
 */
object ScalaXMLProcessing extends App {
  println(<a><b><d></d></b><b><e></e></b><c/></a> \ "b" \ "e")

  val citationIndexes = <CitationIndexes ShowOnSpringerDotCom="Y">
    <CitationIndex Year="2009" Rank="50" NoJournals="50" ImpactFactor="1">
      <Category Term="not defined">ND</Category>
    </CitationIndex>
    <CitationIndex Year="2012" Rank="60" NoJournals="60" ImpactFactor="2">
      <Category Term="not defined">ND</Category>
    </CitationIndex>
    <CitationIndex Year="2013" Rank="60" NoJournals="65">
      <Category Term="not defined">ND</Category>
    </CitationIndex>
    <CitationIndex Year="2011" Rank="59" NoJournals="59" ImpactFactor="3">
      <Category Term="not defined">ND</Category>
    </CitationIndex>
  </CitationIndexes>

  println((citationIndexes \ "CitationIndex")
    .filter(citationIndex => (citationIndex \ "@ImpactFactor").text != "")
    .sortBy(node => (node \ "@Year").text)
    .reverse.headOption
    .map(citationIndex => (citationIndex \ "@ImpactFactor").text)
  )

  val article =
    <Publisher>
      <Journal>
        <Volume>
          <Issue>
            <Article>
              <Body>
                <Section1>
                  <Para>This is a paragraph <InlineEquation>
                    <EquationSource Format="BLAH"></EquationSource>
                  </InlineEquation>
                  </Para>
                  <Para>This is a paragraph <InlineEquation>
                      <EquationSource Format="TEX"></EquationSource>
                    </InlineEquation>
                  </Para>
                </Section1>
              </Body>
            </Article>
          </Issue>
        </Volume>
      </Journal>
    </Publisher>

  def isMathjaxEquation: (Node) => Boolean = {
    equation => (equation \ "@Format").text == "TEX" || (equation \ "@Format").text == "MATHML"
  }

  val result =
    (article \\ "Equation" \ "EquationSource").exists(isMathjaxEquation) ||
    (article \\ "InlineEquation" \ "EquationSource").exists(isMathjaxEquation)

  println(result)
}
