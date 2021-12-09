import com.typesafe.sbt.web.Import.{Assets, pipelineStages}
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import play.sbt.PlayImport.guice
import sbt.Keys._
import sbt._
import sbtmolecule.MoleculePlugin.autoImport.{moleculeDataModelPaths, moleculePluginActive}
import webscalajs.WebScalaJS.autoImport.scalaJSPipeline


object Settings {

  private val common: Seq[Def.Setting[_]] = Seq(
    organization := "org.scalamolecule",
    version := "1.0.0",
    ThisBuild / scalaVersion := "2.13.7",

    // Access to free, but proprietary Client dev-local dependency needed.
    // Please download from https://cognitect.com/dev-tools and install locally per included instructions
    resolvers += Resolver.mavenLocal,

    // Avoid Akka semver error
    ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-java8-compat" % "always"
  )

  val client: Seq[Def.Setting[_]] = common ++ Seq(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "1.2.0",
      ("org.scalamolecule" %%% "molecule" % "1.0.0")
        // Exclude datomic on js platform
        .exclude("com.datomic", "datomic-free")
    )
  )

  val server: Seq[Def.Setting[_]] = common ++ Seq(
    resolvers += "clojars" at "https://clojars.org/repo",
    libraryDependencies ++= Seq(guice,
      "org.scalamolecule" %% "molecule" % "1.0.0",
      "com.vmunier" %% "scalajs-scripts" % "1.2.0",
      "com.datomic" % "datomic-free" % "0.9.5697",
    ).map(_.exclude("org.slf4j", "slf4j-nop")),
    Assets / pipelineStages := Seq(scalaJSPipeline),
    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    Global / onLoad := (Global / onLoad).value.andThen(state => "project server" :: state)
  )

  val shared: Seq[Def.Setting[_]] = common ++ Seq(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.9.4",
      "io.suzaku" %%% "boopickle" % "1.3.3",
      ("org.scalamolecule" %%% "molecule" % "1.0.0")
        // Exclude datomic on js platform
        .exclude("com.datomic", "datomic-free")
    ),
    moleculePluginActive := sys.props.get("molecule").contains("true"),
    moleculeDataModelPaths := Seq("db"), // path to data model

    // Let Intellij detect jars created by sbt-molecule in unmanaged lib directory
    exportJars := true
  )
}
