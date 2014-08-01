import sbt._
import Keys._

name := "RoboWars"

organization := "com.ldaniels528"

version := "0.1"

scalaVersion := "2.11.2"

scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-target:jvm-1.7", "-unchecked",
    "-Ywarn-adapted-args", "-Ywarn-value-discard", "-Xlint")

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked", "-source", "1.7", "-target", "1.7", "-g:vars")

// General Dependencies
libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.3.4",
	"commons-io" % "commons-io" % "2.4",
	"org.slf4j" % "slf4j-api" % "1.7.6",
	"org.scala-lang" % "scala-library" % "2.11.2",
	"org.scala-lang.modules" % "scala-xml_2.11" % "1.0.2"
)
	
// Testing Dependencies
libraryDependencies ++= Seq(
	"junit" % "junit" % "4.11" % "test"
)

resolvers ++= Seq(
	"Local SBT" at "file://" + Path.userHome.absolutePath + "/.ivy2/local",
	"Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    "Java Net" at "http://download.java.net/maven/2/",
    "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Sonatype Repository" at "http://oss.sonatype.org/content/repositories/releases/"
)
                  
resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
