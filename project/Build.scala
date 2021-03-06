import sbt._
import Keys._
import play.Project._
 
object ApplicationBuild extends Build {
 
  val appName         = "users-restws-play2_1"
  val appVersion      = "1.0"
 
  val appDependencies = Seq(
      "play" %% "anorm" % "2.1.0",
      "play" %% "play-jdbc" % "2.1.0"
  )
 
  val main = play.Project(
    appName, appVersion, appDependencies
  ) 
 
}
