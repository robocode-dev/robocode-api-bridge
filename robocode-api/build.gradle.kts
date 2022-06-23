plugins {
    `java-library`
}

group = "dev.robocode"
version = "0.0.2"

repositories {
    mavenLocal()
//    mavenCentral()
}

dependencies {
    implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:0.14.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
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