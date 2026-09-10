plugins {
    java
    idea
    alias(libs.plugins.ben.manes.versions)  // ./gradlew dependencyUpdates
}

group = "dev.robocode"
version = "0.5.0"

val tankRoyaleBotApiVersion = providers.gradleProperty("tankRoyaleBotApiVersion")
    .orElse(libs.versions.tank.royale.bot.api)

repositories {
    // CI resolves the published default. A live conformance run overrides its version with a
    // locally published Tank Royale Bot API from the same revision as the runner (C-002).
    mavenLocal()
    mavenCentral()
}

dependencies {
   implementation(libs.tank.royale.bot.api) {
       version {
           require(tankRoyaleBotApiVersion.get())
       }
   }

   // Tier 1 of the evidence strategy (PDR-001): unit tests over the adapter's value
   // conversions. No engine, so this is the only tier that runs in CI.
   testImplementation(platform(libs.junit.bom))
   testImplementation(libs.junit.jupiter)
   testRuntimeOnly(libs.junit.platform.launcher)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
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
