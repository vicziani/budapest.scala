
name := "root"

version := "0.1-SNAPSHOT"

Common.commonSettings

lazy val frontend = Common.project("frontend")

lazy val backend = Common.project("backend")

lazy val root = Common.root(frontend, backend)
