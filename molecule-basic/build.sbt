
name := "molecule-basic"
version := "0.18.0"
organization := "org.scalamolecule"
scalaVersion := "3.6.4"

libraryDependencies ++= Seq(
  "org.scalamolecule" %% "molecule-datalog-datomic" % "0.18.0",
  "org.scalamolecule" %% "molecule-sql-h2" % "0.18.0",
  "org.scalameta" %% "munit" % "1.0.3" % Test,

  // Enforce one version to avoid warnings of multiple dependency versions when running tests
  "org.slf4j" % "slf4j-nop" % "2.0.17"
)
testFrameworks := Seq(new TestFramework("munit.Framework"))
Test / parallelExecution := false
Test / fork := true

// Molecule plugin that generates molecule boilerplate code from your data model
enablePlugins(MoleculePlugin)

// Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
moleculePluginActive := sys.props.get("molecule").contains("true")

// Where to find your domain structure definitions
moleculeDomainPaths := Seq("app/domain")

// Optionally generate source files instead of jars.
//moleculeMakeJars := false