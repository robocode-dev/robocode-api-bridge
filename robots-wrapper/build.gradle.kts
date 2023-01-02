plugins {
    `java`
}

group = "dev.robocode"
version = "0.1.2"

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