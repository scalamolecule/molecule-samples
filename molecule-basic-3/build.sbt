
name := "molecule-basic-3"
version := "0.14.1"
organization := "org.scalamolecule"
scalaVersion := "3.3.4"

libraryDependencies ++= Seq(
  // Molecule APIs
  "org.scalamolecule" %% "molecule-datalog-datomic" % "0.14.1",
  "org.scalamolecule" %% "molecule-sql-h2" % "0.14.1",

  // (transitional dependencies on zio and cats-effect from molecule.core)

  // Test dependencies
  "com.lihaoyi" %% "utest" % "0.8.4" % Test,
  "dev.zio" %% "zio-test" % "2.0.15" % Test,
  "dev.zio" %% "zio-test-sbt" % "2.0.15" % Test,
  "org.typelevel" %% "munit-cats-effect" % "2.0.0" % Test,
)
testFrameworks := Seq(
  new TestFramework("utest.runner.Framework"), // for sync/async tests
  new TestFramework("zio.test.sbt.ZTestFramework"), // for zio test
  new TestFramework("munit.Framework"), // For cats.effect.IO test
)

// Run tests for all systems sequentially to avoid data locks with db
// Only applies on JVM. On JS platform there's no parallelism anyway.
Test / parallelExecution := false

// Molecule plugin that generates molecule boilerplate code from your data model
enablePlugins(MoleculePlugin)

// Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
moleculePluginActive := sys.props.get("molecule").contains("true")

moleculeDataModelPaths := Seq("app/dataModel")
