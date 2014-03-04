name := "Scala Sketchpad"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "7.0.0",
    "org.specs2" %% "specs2" % "2.3.8" % "test",
    "com.escalatesoft.subcut" %% "subcut" % "2.0"
)