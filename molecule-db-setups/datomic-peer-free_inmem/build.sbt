import sbt.Keys._

lazy val app = project.in(file("."))
  .enablePlugins(MoleculePlugin)
  .settings(
    name := "datomic-peer-free_inmem",
    scalaVersion := "2.13.8",
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      "clojars" at "https://clojars.org/repo",
    ),
    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "1.1.0"
    ),

    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule").contains("true"),

    // Path to domain data model directory
    moleculeDataModelPaths := Seq("app"),

    // Let Intellij detect jars created by sbt-molecule in unmanaged lib directory
    exportJars := true
  )
