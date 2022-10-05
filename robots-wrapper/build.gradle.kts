plugins {
    `java`
}

group = "dev.robocode"
version = "0.1.1"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = sourceCompatibility
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "Main"
        }
    }
}