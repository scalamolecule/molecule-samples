
lazy val root = (project in file("."))
  .aggregate(client, server, shared.js, shared.jvm)
  .settings(name := "molecule-rpc-transparent")


lazy val client = (project in file("client"))
  .dependsOn(shared.js)
  .enablePlugins(ScalaJSWeb)
  .settings(Settings.client)


lazy val server = (project in file("server"))
  .dependsOn(shared.jvm)
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .settings(Settings.server, scalaJSProjects := Seq(client))


lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .enablePlugins(MoleculePlugin)
  .settings(Settings.shared)
