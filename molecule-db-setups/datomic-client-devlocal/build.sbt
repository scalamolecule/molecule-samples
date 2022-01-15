import sbt.Keys._


lazy val app = project.in(file("."))
  .enablePlugins(MoleculePlugin)
  .settings(
    name := "datomic-client-devlocal",
    scalaVersion := "2.13.8",
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      "clojars" at "https://clojars.org/repo",

      // Molecule needs the free but proprietary dev-local dependency to be available on the class path.
      // Please download from https://cognitect.com/dev-tools and install locally per included instructions.
      Resolver.mavenLocal
    ),

    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "1.1.0",
      "com.datomic" % "dev-local" % "1.0.238"
    ),

    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule").contains("true"),

    // Path to domain data model directory
    moleculeDataModelPaths := Seq("app"),

    // Let IDE detect created jars in unmanaged lib directory
    exportJars := true
  )
