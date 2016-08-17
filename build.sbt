name := """sh-backend-play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
//  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0"
)


libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.191"
)

libraryDependencies += "net.liftweb" %% "lift-json" % "2.6"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

//version := "1.0"
//
//scalaVersion := "2.10.1"
//
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor"   % "2.2-M3",
//  "com.typesafe.akka" %% "akka-slf4j"   % "2.2-M3",
//  "com.typesafe.akka" %% "akka-remote"  % "2.2-M3",
//  "com.typesafe.akka" %% "akka-agent"   % "2.2-M3",
//  "com.typesafe.akka" %% "akka-testkit" % "2.2-M3" % "test"
//)


