import java.time.Duration

plugins {
    java
}

group = "dev.robocode"
version = "0.5.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = sourceCompatibility
}

// Tier 2 of the evidence strategy (PDR-001). These tests drive both engines through the
// compatibility harness, so they need a classic Robocode installation, the classic source
// repository's compiled test robots, and the Tank Royale runner jar. None of those exist on
// a CI runner, so the tier skips rather than fails when the environment is absent -- a
// clean checkout must still build.
//
// Note the module deliberately declares no engine dependency. The bridge reproduces the
// `robocode.*` package that classic's own jars define, so the two cannot share a classpath;
// each engine runs in its own process (ARCH-003).
tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("failed", "skipped")
        }

        // Passed through to the test beds. Override with -P on the command line.
        for (property in listOf(
                "robocode.home",        // a working classic Robocode installation
                "robocode.source",      // the classic source repository, for its test robots
                "conformance.python",   // python interpreter running the harness
                "conformance.rounds")) {
            (project.findProperty(property) as String?)?.let { systemProperty(property, it) }
        }

        // The beds start engines, so a battle takes seconds rather than milliseconds.
        timeout.set(Duration.ofMinutes(30))
    }
}
