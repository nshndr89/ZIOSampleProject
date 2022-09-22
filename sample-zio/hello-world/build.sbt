val scala3Version = "2.13.7"
val zioVersion = "2.0.0"
val tapirVersion = "1.1.0"
lazy val root = project
  .in(file("."))
  .settings(
    name := "hello-world",
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
                                "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion
                                )
  )
