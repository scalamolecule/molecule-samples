
name := "molecule-basic-3"
version := "0.15.1"
organization := "org.scalamolecule"
scalaVersion := "3.3.4"

libraryDependencies ++= Seq(
  "org.scalamolecule" %% "molecule-datalog-datomic" % "0.15.1",
  "org.scalamolecule" %% "molecule-sql-h2" % "0.15.1",
  "org.scalameta" %% "munit" % "1.0.3" % Test,
)
testFrameworks := Seq(new TestFramework("munit.Framework"))
Test / parallelExecution := false
//Test / fork := true

// Molecule plugin that generates molecule boilerplate code from your data model
enablePlugins(MoleculePlugin)

// Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
moleculePluginActive := sys.props.get("molecule").contains("true")

// Where to find your domain structure definitions
moleculeDomainPaths := Seq("app/domain")

// Optionally generate source files instead of jars.
//moleculeMakeJars := false