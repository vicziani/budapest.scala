import sbt._
import Keys._ // sbt.Keys.

object Common {
	val org = "org.budapest.scala"

	val commonSettings = Seq(
		scalaVersion := "2.10.0",
		organization := org,
		libraryDependencies ++= Seq()
	)

	lazy val isCiBuild = (if (System.getProperty("CI_BUILD") == null) false else true)

	lazy val utilsAsProjectRef = ProjectRef(file("../utils"), "utils")

	lazy val utilsAsDependency = org %% "utils" % "0.1-SNAPSHOT"
	
	def project(name: String): Project = {
		val prj = Project(id = name, base = file(name)).settings(commonSettings: _*)
		if (isCiBuild) 
			prj.settings(libraryDependencies += utilsAsDependency)
		else
			prj.dependsOn(utilsAsProjectRef)
	}
	// NOTE: ProjectRef != ProjectReference
	def root(subprojects: ProjectReference*): Project = Project("root", file(".")).aggregate(subprojects: _*)

}
