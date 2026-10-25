import java.io.File

plugins {
    java
    idea
    alias(libs.plugins.ben.manes.versions)  // ./gradlew dependencyUpdates
}

group = "dev.robocode"
version = "0.3.1"

repositories {
    if (providers.gradleProperty("tankRoyaleBotApiVersion").isPresent) mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":robocode-api"))
    implementation(libs.bcel)
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
    withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
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

    test {
        useJUnitPlatform()
        javaLauncher.set(project.extensions.getByType<org.gradle.jvm.toolchain.JavaToolchainService>().launcherFor {
            languageVersion.set(JavaLanguageVersion.of(providers.gradleProperty("previewTestJavaVersion").orElse("11").get().toInt()))
        })
        dependsOn(fatJar, ":robocode-api:jar")
        systemProperty("preview.root", rootProject.projectDir.absolutePath)
        systemProperty("preview.wrapper", fatJar.get().archiveFile.get().asFile.absolutePath)
        systemProperty("preview.adapter", project(":robocode-api").tasks.named<Jar>("jar").get().archiveFile.get().asFile.absolutePath)
        systemProperty("preview.dependencies", configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
        testLogging { events("failed", "skipped") }
    }

    register<JavaExec>("previewDist") {
        group = "distribution"
        description = "Prepare a clean, revision-pinned offline-use preview ZIP and checksum; never publishes."
        dependsOn(fatJar, ":robocode-api:jar")
        classpath = sourceSets.main.get().runtimeClasspath
        mainClass.set("PreviewArchive")
        outputs.upToDateWhen { false }
        doFirst {
            if (providers.gradleProperty("tankRoyaleBotApiVersion").isPresent) {
                throw GradleException("Preview candidates cannot use tankRoyaleBotApiVersion overrides")
            }
            setArgs(listOf(
                rootProject.projectDir.absolutePath,
                layout.buildDirectory.dir("distributions").get().asFile.absolutePath,
                providers.gradleProperty("previewVersion").orElse("").get(),
                project(":robocode-api").version.toString(),
                libs.versions.tank.royale.bot.api.get(),
                fatJar.get().archiveFile.get().asFile.absolutePath,
                project(":robocode-api").tasks.named<Jar>("jar").get().archiveFile.get().asFile.absolutePath
            ) + configurations.runtimeClasspath.get().files.sortedBy { it.name }.map { it.absolutePath })
        }
    }
}
