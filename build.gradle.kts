import org.gradle.api.tasks.testing.Test

plugins {
    java
    alias(libs.plugins.kotlin.jvm) apply false
}

val testForks = providers.gradleProperty("test.maxParallelForks")
    .map(String::toInt)
    .orElse(Runtime.getRuntime().availableProcessors().coerceAtMost(8).coerceAtLeast(1))

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    tasks.withType<Test>().configureEach {
        // Test forks are isolated JVMs. Harness-backed tests assign each fork its own work dir.
        maxParallelForks = testForks.get()
    }
}
