name := "Scala Sketchpad"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "7.1.0",
    "org.specs2" %% "specs2-core" % "2.4.12" % "test",
    "com.escalatesoft.subcut" %% "subcut" % "2.0",
    "com.github.scaldi" %% "scaldi" % "0.2",
    "com.typesafe.play" %% "play-json" % "2.2.1",
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.2" withSources()
)