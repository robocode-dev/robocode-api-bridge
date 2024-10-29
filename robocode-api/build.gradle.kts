plugins {
    java
    idea
}

group = "dev.robocode"
version = "0.2.3"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:0.25.0")
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