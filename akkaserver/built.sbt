val AkkaVersion = "2.6.17"

resolvers += ("custome1" at "http://4thline.org/m2").withAllowInsecureProtocol(true)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-remote" % AkkaVersion,
  "com.typesafe.akka" %% "akka-cluster-typed" % AkkaVersion,
  "org.fourthline.cling" % "cling-core" % "2.1.2",
 "org.fourthline.cling" % "cling-support" % "2.1.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalafx" %% "scalafx" % "8.0.192-R14",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

fork:= false

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m=>
  "org.openjfx" % s"javafx-$m" % "11" classifier osName
)