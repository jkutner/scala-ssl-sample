resolvers += Resolver.url("heroku-sbt-plugin-releases",
  url("http://dl.bintray.com/heroku/sbt-plugins/"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.7.4")

addSbtPlugin("com.heroku" % "sbt-heroku" % "0.1.2")
