lazy val commonSettings = Seq(
  version := "1.0.0",
  organization := "au.com.scala.test",
  scalaVersion := "2.12.6",
  test in assembly :={}

)

lazy val root = (project in file("."))
  .settings(commonSettings:  _*)
  .settings(
    name := "data-compressor",
    mainClass in assembly := Some("au.com.bt.encoding.StringEncoder")
  )

libraryDependencies ++= Seq(
  "org.rogach"       %% "scallop"         % "2.1.1",
  "org.slf4j"         % "slf4j-log4j12"   % "1.2",
  "org.scalatest"    %% "scalatest"       % "3.0.1"  % "test"
)
