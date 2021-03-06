import sbt.Keys._

lazy val app = project.in(file("."))
  .enablePlugins(MoleculePlugin)
  .settings(
    name := "molecule-basic",
    scalaVersion := "2.13.8",
    resolvers ++= Seq(
      "clojars" at "https://clojars.org/repo",
    ),
    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "1.1.0",
    ),

    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule").contains("true"),

    // Path to domain data model directory
    moleculeDataModelPaths := Seq("app"),

    // Let Intellij detect jars created by sbt-molecule in unmanaged lib directory
    exportJars := true
  )
