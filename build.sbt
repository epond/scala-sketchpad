name := "Scala Sketchpad"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "7.1.0",
    "org.specs2" %% "specs2-core" % "2.4.12" % "test",
    "com.escalatesoft.subcut" %% "subcut" % "2.1",
    "org.scaldi" %% "scaldi" % "0.5.7",
    "com.typesafe.play" %% "play-json" % "2.3.4",
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.2" withSources()
)