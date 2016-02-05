package sketchpad.core

// based upon http://danielwestheide.com/blog/2013/01/02/the-neophytes-guide-to-scala-part-7-the-either-type.html
object Either extends App {
  import scala.io.Source
  import java.net.URL

  // Censorship!
  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked for the good of the people!")
    else
      Right(Source.fromURL(url))

  getContent(new URL("http://google.com")) match {
    case Left(msg) => println(msg)
    case Right(source) => source.getLines.foreach(println)
  }

  // Either is unbiased and has projection types which isn't ideal
  def averageLineCount(url1: URL, url2: URL): Either[String, Int] =
    for {
      source1 <- getContent(url1).right
      source2 <- getContent(url2).right
      // It would be nice to do something like the following two lines but we can't because it would introduce
      // a new call to map() on the previous call to map() which returned an Either which doesn't define map()
      //lines1 = source1.getLines().size
      //lines2 = source2.getLines().size
      // A workaround is to replace any value definitions with generators
      lines1 <- Right(source1.getLines().size).right
      lines2 <- Right(source2.getLines().size).right
    } yield (lines1 + lines2) / 2
}
