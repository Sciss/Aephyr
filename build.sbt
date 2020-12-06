lazy val baseName = "TreeTable"
lazy val baseNameL = baseName.toLowerCase

lazy val projectVersion = "1.6.1"
lazy val mimaVersion    = "1.6.0"

name := baseName

// ---- dependencies ----

lazy val deps = new {
  val main = new {
    val swingPlus = "0.5.0"
  }
  val test = new {
    val submin    = "0.3.4"
  }
}

def basicJavaOpts = Seq("-source", "1.8")

// sonatype plugin requires that these are in global
ThisBuild / version      := projectVersion
ThisBuild / organization := "de.sciss"

lazy val commonSettings = Seq(
//  version            := projectVersion,
//  organization       := "de.sciss",
  scalaVersion       := "2.13.4",
  crossScalaVersions := Seq("3.0.0-M2", "2.13.4", "2.12.12"),
  javacOptions                   := basicJavaOpts ++ Seq("-encoding", "utf8", "-Xlint:unchecked", "-target", "1.8"),
  javacOptions in (Compile, doc) := basicJavaOpts,  // doesn't eat `-encoding` or `target`
  description        := "A TreeTable component for Swing",
  homepage           := Some(url(s"https://git.iem.at/sciss/${name.value}")),
  licenses           := Seq("LGPL v3+" -> url("http://www.gnu.org/licenses/lgpl-3.0.txt"))
) ++ publishSettings

// ---- publishing ----

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    Some(if (isSnapshot.value)
      "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    else
      "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
    )
  },
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false }
)

lazy val root = project.withId(baseNameL).in(file("."))
  .aggregate(javaProject, scalaProject)
  .dependsOn(javaProject, scalaProject) // i.e. root = full sub project. if you depend on root, will draw all sub modules.
  .settings(commonSettings)
  .settings(
    packagedArtifacts := Map.empty           // prevent publishing anything!
  )

lazy val javaProject = project.withId(s"$baseNameL-java").in(file("java"))
  .settings(commonSettings)
  .settings(
    autoScalaLibrary := false,
    crossPaths       := false,
    javacOptions in Compile ++= Seq("-g", "-target", "1.8", "-source", "1.8"),
    javacOptions in (Compile, doc) := Nil,  // yeah right, sssssuckers
    publishArtifact := {
      val old = publishArtifact.value
      old && scalaVersion.value.startsWith("2.13")  // only publish once when cross-building
    },
    pomExtra := pomExtraBoth,
    mimaPreviousArtifacts := Set("de.sciss" % s"$baseNameL-java" % mimaVersion)
  )

lazy val scalaProject = project.withId(s"$baseNameL-scala").in(file("scala"))
  .dependsOn(javaProject)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "de.sciss" %% "swingplus" % deps.main.swingPlus,
      "de.sciss" %  "submin"    % deps.test.submin % Test
    ),
    pomExtra := pomBase ++ pomDevsSciss,
    mimaPreviousArtifacts := Set("de.sciss" %% s"$baseNameL-scala" % mimaVersion)
  )

def pomExtraBoth = pomBase ++ pomDevsBoth

def pomBase =
  <scm>
    <url>git@git.iem.at:sciss/TreeTable.git</url>
    <connection>scm:git:git@git.iem.at:sciss/TreeTable.git</connection>
  </scm>

def pomDevSciss =
  <developer>
    <id>sciss</id>
    <name>Hanns Holger Rutz</name>
    <url>http://www.sciss.de</url>
  </developer>

def pomDevAephyr =
  <developer>
    <id>aephyr</id>
    <name>unknown</name>
    <url>http://code.google.com/p/aephyr/</url>
  </developer>

def pomDevsBoth =
  <developers>
    {pomDevSciss}
    {pomDevAephyr}
  </developers>

def pomDevsSciss =
  <developers>
    {pomDevSciss}
  </developers>

