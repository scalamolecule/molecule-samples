import sbt.Keys._

lazy val app = project.in(file("."))
  .enablePlugins(MoleculePlugin)
  .settings(
    name := "datomic-client-peerserver_inmem",
    scalaVersion := "2.13.8",
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      "clojars" at "https://clojars.org/repo",
      "my.datomic.com" at "https://my.datomic.com/repo",

      // Molecule needs the free but proprietary pro dependency to be available on the class path.
      // Please download from https://www.datomic.com/get-datomic.html and install locally per included instructions.
      Resolver.mavenLocal
    ),
    /*
      Downloading Datomic Starter/Pro requires authentication of your license:
      Create a ~/.sbt/.credentials file with the following content:

        realm=Datomic Maven Repo
        host=my.datomic.com
        id=my.datomic.com
        user=<your-username>
        pass=<your-password>

      Then let sbt provide your secret credentials:
    */
    credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),

    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "1.1.0",
      "com.datomic" % "datomic-pro" % "1.0.6344"
    ),

    // Important to exclude free version when using pro to avoid clashes with pro version
    excludeDependencies += ExclusionRule("com.datomic", "datomic-free"),

    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule").contains("true"),

    // Path to domain data model directory
    moleculeDataModelPaths := Seq("app"),

    // Let IDE detect created jars in unmanaged lib directory
    exportJars := true
  )
