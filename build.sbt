import Dependencies._

useGpg := true

lazy val root = (project in file("."))
  .settings(
    name := "typo-detector",
    libraryDependencies += scalaTest % Test
  )

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / name             := "typo-detector"
ThisBuild / organization     := "io.github.antivanov"
ThisBuild / licenses         := Seq("MIT  " -> url("https://opensource.org/licenses/MIT"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/antivanov/typo-detector"),
    "scm:git@github.com:antivanov/typo-detector.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id    = "antivanov",
    name  = "Anton Ivanov",
    email = "anton.al.ivanov@gmail.com",
    url   = url("https://github.com/antivanov")
  )
)

ThisBuild / homepage := Some(url("https://github.com/antivanov/typo-detector"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
