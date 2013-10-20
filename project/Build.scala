import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

	val appName         = "ConnectedCaching"
	val appVersion      = "0.1-SNAPSHOT"

	val appDependencies = Seq(
		jdbc,
		cache,
		"org.webjars" % "webjars-play_2.10" % "2.2.0",
		"org.webjars" % "bootstrap" % "3.0.0",
		"org.webjars" % "extjs" % "4.2.1.883",
		// change to official release when available
		"com.micronautics" % "securesocial" % "2.2.0"
	)

	val main = play.Project(appName, appVersion, appDependencies).settings(

		resolvers += Resolver.url(
			"sbt-plugin-snapshots",
			new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/")
		)(Resolver.ivyStylePatterns)

	)

}