val scala3Version = "3.2.0"

lazy val root = project
  .in(file("."))
  .settings(
    name         := "tagless-final-pizza",
    version      := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.3.14",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "ch.qos.logback" % "logback-classic" % "1.4.3"
    )
  )