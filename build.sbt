name := """srp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.projectlombok" % "lombok" % "1.16.6",
  "joda-time" % "joda-time" % "2.9.1",
  "mysql" % "mysql-connector-java" % "5.1.20",
   "org.mindrot" % "jbcrypt" % "0.3m",
   "com.typesafe.play" %% "play-mailer" % "3.0.1",
   "org.apache.commons" % "commons-email" % "1.3.3",
   evolutions
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

//fork in run := true