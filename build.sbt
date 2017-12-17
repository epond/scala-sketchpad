name := "Scala Sketchpad"

version := "1.0"

scalaVersion := "2.12.4"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "7.3.0-M18",
    "org.specs2" %% "specs2-core" % "4.0.0" % "test",
    "com.typesafe.play" %% "play-json" % "2.6.7",
    "com.typesafe.play" %% "play-json-joda" % "2.6.7",
    "net.databinder.dispatch" %% "dispatch-core" % "0.13.2" withSources(),
    "com.github.mpilquist" %% "simulacrum" % "0.11.0"
)