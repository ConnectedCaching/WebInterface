name := "WebInterface"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
    jdbc,
    anorm,
    cache,
    "org.webjars" % "webjars-play_2.10" % "2.2.0",
    "org.webjars" % "bootstrap" % "3.0.0",
    "org.webjars" % "extjs" % "4.2.1.883"
)

play.Project.playScalaSettings
