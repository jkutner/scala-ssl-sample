import NativePackagerKeys._

packageArchetype.java_application

name := """scala-getting-started"""

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.twitter" % "finagle-http_2.10" % "6.18.0",
  "com.stripe" % "stripe-java" % "1.18.0"
)

herokuJdkVersion in Compile := "1.8"

herokuJdkUrl in Compile := "http://heroku-jdk.s3.amazonaws.com/openjdk1.8.0_b107.tar.gz"

herokuAppName in Compile := "polar-citadel-1715"
