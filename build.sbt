val scala3Version = "3.7.4"
val zio2Version = "2.1.6"

lazy val root = project
  .in(file("."))
  .settings(
    name := "ZIOPractice",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    // libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,
    // libraryDependencies += "dev.zio"    %% "zio"          % zio2Version,
    
    libraryDependencies ++= Seq(
    "org.scalameta" %% "munit" % "1.0.0" % Test,
    "dev.zio"    %% "zio"          % zio2Version,
    "dev.zio"    %% "zio-http"     % "3.0.1",
    "dev.zio"    %% "zio-json"     % "0.6.2",
    "org.apache.pdfbox" % "pdfbox" % "2.0.30",
    )
  )
