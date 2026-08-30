plugins {
    java
    idea
    id("com.github.ben-manes.versions") version "0.52.0"  // ./gradlew dependencyUpdates
}

group = "dev.robocode"
version = "0.5.0"

repositories {
    // bot-api 1.0.2 is on Maven Central. mavenLocal stays first so an unreleased build can
    // be tried against the bridge by publishing it with `gradlew :bot-api:java:publishToMavenLocal`
    // in the tank-royale repository -- but C-002 applies: the version must stay protocol
    // compatible with the server embedded in the runner jar, and nothing checks that.
    mavenLocal()
    mavenCentral()
}

dependencies {
   implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:1.0.2")

   // Tier 1 of the evidence strategy (PDR-001): unit tests over the adapter's value
   // conversions. No engine, so this is the only tier that runs in CI.
   testImplementation(platform("org.junit:junit-bom:5.11.4"))
   testImplementation("org.junit.jupiter:junit-jupiter")
   testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = sourceCompatibility
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("failed")
        }
    }

    jar {
        manifest {
            attributes["Implementation-Title"] = "Robocode API for Robocode Tank Royale"
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = "robocode.dev"
            attributes["Package"] = project.group
        }
    }
}