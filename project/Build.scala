import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

	val appName         = "ConnectedCaching"
	val appVersion      = "0.1-SNAPSHOT"

	val appDependencies = Seq(
		cache,
		"org.webjars" % "webjars-play_2.10" % "2.2.0",
		"org.webjars" % "bootstrap" % "3.0.0",
		"org.webjars" % "extjs" % "4.2.1.883",
		"com.feth" %%  "play-authenticate" % "0.5.0-SNAPSHOT",
		"org.reactivemongo" %% "play2-reactivemongo" % "0.10.0-SNAPSHOT",
		"play" %% "fastforward" % "0.1-SNAPSHOT"
	)

	val main = play.Project(appName, appVersion, appDependencies).settings(
		resolvers += "FastForward (snapshots)" at "http://repo.acira.net/snapshots/",
		resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
		resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),
		resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
		resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns),
		// required for Reactive Mongo Snapshots
		resolvers += "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
	)

}