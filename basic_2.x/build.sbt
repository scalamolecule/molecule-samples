
name := "basic_2.x"
version := "0.1.0"
organization := "org.scalamolecule"

// scalaVersion := "2.12.18" // swap and re-compile to test
scalaVersion := "2.13.11"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "utest" % "0.8.1",
  "org.scalamolecule" %% "molecule-datalog-datomic" % "0.1.0",
  "org.scalamolecule" %% "molecule-sql-jdbc" % "0.1.0",
)
testFrameworks := Seq(
  new TestFramework("utest.runner.Framework"),
  new TestFramework("zio.test.sbt.ZTestFramework")
)

// Molecule plugin that generates molecule boilerplate code from your data model
enablePlugins(MoleculePlugin)

// Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
moleculePluginActive := sys.props.get("molecule").contains("true")

moleculeDataModelPaths := Seq("app")


