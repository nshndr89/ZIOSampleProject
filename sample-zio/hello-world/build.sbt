
val scala3Version = "2.13.7"
val zioVersion = "2.0.0"
val tapirVersion = "1.1.1"
lazy val root = project
  .in(file("."))
  .settings(
    name := "hello-world",
    scalaSource in Test := baseDirectory.value / "src/test/scala",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies ++= Seq(
                                "dev.zio" %% "zio" % zioVersion,
                                "dev.zio" %% "zio-test" % zioVersion,
                                "dev.zio" %% "zio-test-sbt" % zioVersion,
                                "dev.zio" %% "zio-streams" % zioVersion,
                                "dev.zio" %% "zio-test-junit" % zioVersion,
                                "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
                                "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
                                "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
                                "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.8.0",
                                "com.softwaremill.sttp.client3" %% "circe" % "3.8.0",
                                "io.circe" %% "circe-generic" % "0.14.1",
                                "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % "1.1.1",
                                "com.softwaremill.sttp.client3" %% "zio" % "3.8.0",
                                "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % "3.8.0",
                                "dev.zio" %% "zio-managed" % zioVersion
                                )
  )
