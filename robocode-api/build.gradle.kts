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
    // Resolve the default from Maven Central, even when a different local build has the same
    // version. Conformance can explicitly select a locally published API matched to its runner.
    if (providers.gradleProperty("tankRoyaleBotApiVersion").isPresent) {
        mavenLocal()
    }
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
