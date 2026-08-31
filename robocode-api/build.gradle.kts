plugins {
    java
    idea
    id("com.github.ben-manes.versions") version "0.52.0"  // ./gradlew dependencyUpdates
}

group = "dev.robocode"
version = "0.5.0"

val tankRoyaleBotApiVersion = providers.gradleProperty("tankRoyaleBotApiVersion").orElse("1.0.2")

repositories {
    // CI resolves the published default. A live conformance run overrides its version with a
    // locally published Tank Royale Bot API from the same revision as the runner (C-002).
    mavenLocal()
    mavenCentral()
}

dependencies {
   implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:${tankRoyaleBotApiVersion.get()}")

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
