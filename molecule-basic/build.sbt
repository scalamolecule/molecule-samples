
name := "molecule-basic"
version := "0.22.0"
organization := "org.scalamolecule"
scalaVersion := "3.7.1"

libraryDependencies ++= Seq(
  "org.scalamolecule" %% "molecule-db-datalog-datomic" % "0.22.0",
  "org.scalamolecule" %% "molecule-db-sql-h2" % "0.22.0",
  "org.scalameta" %% "munit" % "1.1.1" % Test,

  // Enforce one version to avoid warnings of multiple dependency versions when running tests
  "org.slf4j" % "slf4j-nop" % "2.0.17"
)
testFrameworks := Seq(new TestFramework("munit.Framework"))
Test / parallelExecution := false
Test / fork := true

enablePlugins(MoleculePlugin)