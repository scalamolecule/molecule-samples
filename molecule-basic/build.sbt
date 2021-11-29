import sbt.Keys._



lazy val `molecule-basic` = project.in(file("."))
  .enablePlugins(MoleculePlugin)
  .settings(
    scalaVersion := "2.13.7",
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      "clojars" at "https://clojars.org/repo",

      // Molecule needs the free but proprietary dev-local dependency to be available on the class path.
      // Please download from https://cognitect.com/dev-tools and install locally per included instructions.
      Resolver.mavenLocal
    ),

    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "1.0.0",
    ),

    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule").contains("true"),
    moleculeDataModelPaths := Seq("app"), // dir containing domain data model

    // Ensure clojure loads correctly
    // In a scalajs or datomic pro setup it's not necessary
    Compile / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,

    // Let Intellij detect sbt-molecule-created jars in unmanaged lib directory
    exportJars := true
  )
