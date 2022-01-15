import com.typesafe.sbt.web.Import.{Assets, pipelineStages}
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import play.sbt.PlayImport.guice
import sbt.Keys.{resolvers, _}
import sbt._
import sbtmolecule.MoleculePlugin.autoImport.{moleculeDataModelPaths, moleculeMakeJars, moleculePluginActive}
import webscalajs.WebScalaJS.autoImport.scalaJSPipeline


object Settings {

  private val common: Seq[Def.Setting[_]] = Seq(
    organization := "org.scalamolecule",
    version := "1.0.1",
    ThisBuild / scalaVersion := "2.13.8",
    resolvers += "clojars" at "https://clojars.org/repo"
  )

  val client: Seq[Def.Setting[_]] = common ++ Seq(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.0.0",
      "org.scala-js" %%% "scala-js-macrotask-executor" % "1.0.0",
    ),
    // Exclude datomic on js platform
    excludeDependencies += ExclusionRule("com.datomic", "datomic-free"),
  )

  val server: Seq[Def.Setting[_]] = common ++ Seq(
    libraryDependencies ++= Seq(
      guice,
      "com.vmunier" %% "scalajs-scripts" % "1.2.0",
      "com.datomic" % "datomic-free" % "0.9.5697",
    ),
    Assets / pipelineStages := Seq(scalaJSPipeline),

    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    Global / onLoad := (Global / onLoad).value.andThen(state => "project server" :: state)
  )

  val shared: Seq[Def.Setting[_]] = common ++ Seq(
    libraryDependencies ++= Seq(
      "org.scalamolecule" %%% "molecule" % "1.1.0",
      "com.lihaoyi" %%% "scalatags" % "0.11.0",
      "io.suzaku" %%% "boopickle" % "1.4.0",
    ),

    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule").contains("true"),

    // Path to domain data model directory
    moleculeDataModelPaths := Seq("db"),

    // Let Intellij detect jars created by sbt-molecule in unmanaged lib directory
    exportJars := true
  )
}
