import sbt.Keys._
import sbt._

import scala.language.postfixOps

val scalaVersion_2_12 = "2.12.14"
val scalaVersion_2_13 = "2.13.6"
val scalaVersion_3_00 = "3.0.0-M2"

val appVersion = "0.1"
val pluginVersion = "1.0.0"
val scalaAppVersion = scalaVersion_2_12

val akkaVersion = "2.6.15"
val akkaHttpVersion = "10.2.4"
val awsKinesisClientVersion = "1.14.4"
val awsSDKVersion = "1.11.946"
val commonsIOVersion = "2.6"
val liftJsonVersion = "3.4.3"
val scalaTestVersion = "3.3.0-SNAP3"
val slf4jVersion = "1.7.32"
val snappyJavaVersion = "1.1.8.4"

lazy val root = (project in file("."))
  .settings(
    name := "RoboWars",
    organization := "com.github.ldaniels528",
    description := "RoboWars 3D Game",
    version := appVersion,
    scalaVersion := scalaAppVersion,
    Compile / console / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-Xlint"),
    Compile / doc / scalacOptions += "-no-link-warnings",
    autoCompilerPlugins := true,
    assembly / mainClass := Some("com.ldaniels528.robowars.RoboWars"),
    assembly / test := {},
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case PathList("org", "apache", _*) => MergeStrategy.first
      case PathList("akka-http-version.conf") => MergeStrategy.concat
      case PathList("reference.conf") => MergeStrategy.concat
      case PathList("version.conf") => MergeStrategy.concat
      case _ => MergeStrategy.first
    },
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.6.18",
      "commons-io" % "commons-io" % "2.4",
      "org.slf4j" % "slf4j-api" % "1.7.7",
      "org.slf4j" % "slf4j-log4j12" % "1.7.7",
      "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
      // Testing Dependencies
      "junit" % "junit" % "4.11" % "test"
    ))

// loads the Scalajs-io root project at sbt startup
onLoad in Global := (Command.process("project root", _: State)) compose (onLoad in Global).value