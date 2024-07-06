
name := "molecule-basic-2-13"
version := "0.9.0"
organization := "org.scalamolecule"
scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "utest" % "0.8.3",
  "org.scalamolecule" %% "molecule-datalog-datomic" % "0.9.0",
  "org.scalamolecule" %% "molecule-sql-h2" % "0.9.0",
)
testFrameworks := Seq(
  new TestFramework("utest.runner.Framework"),
  new TestFramework("zio.test.sbt.ZTestFramework")
)

// Run tests for all systems sequentially to avoid data locks with db
// Only applies on JVM. On JS platform there's no parallelism anyway.
Test / parallelExecution := false

// Molecule plugin that generates molecule boilerplate code from your data model
enablePlugins(MoleculePlugin)

// Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
moleculePluginActive := sys.props.get("molecule").contains("true")

moleculeDataModelPaths := Seq("app")