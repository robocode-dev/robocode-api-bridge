plugins {
    java
    idea
}

group = "dev.robocode"
version = "0.2.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.bcel:bcel:6.10.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = sourceCompatibility
}

tasks {
    // Remove the default jar task
    named<Jar>("jar") {
        enabled = false
    }

    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(
            listOf(
                "compileJava",
                "processResources"
            )
        )
        // We need this for Gradle optimization to work
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Main-Class"] = "Main"
        }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }

    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}