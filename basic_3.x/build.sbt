
name := "basic_3.x"
version := "0.8.0"
organization := "org.scalamolecule"
scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "utest" % "0.8.1",
  "org.scalamolecule" %% "molecule-datalog-datomic" % "0.8.0",
  "org.scalamolecule" %% "molecule-sql-h2" % "0.8.0",
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

// For some reason, Scala 3.3 can't read generated classes in jars in lib
// (seems like a binary compatibility issue to be solved - there is likely a simple solution).
// So we skip packing the generate source files in jars and leave them as managed sources in the target directory.
moleculeMakeJars := false

