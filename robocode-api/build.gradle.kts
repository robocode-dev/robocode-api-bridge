plugins {
    java
    idea
    id("com.github.ben-manes.versions") version "0.52.0"  // ./gradlew dependencyUpdates
}

group = "dev.robocode"
version = "0.5.0"

repositories {
    mavenLocal() // bot-api 1.0.2 is not published on Maven Central (yet); publish it with
                 // `gradlew :bot-api:java:publishToMavenLocal` in the tank-royale repository
    mavenCentral()
}

dependencies {
   implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:1.0.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = sourceCompatibility
}

tasks {
    jar {
        manifest {
            attributes["Implementation-Title"] = "Robocode API for Robocode Tank Royale"
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = "robocode.dev"
            attributes["Package"] = project.group
        }
    }
}