plugins {
    `java-library`
}

group = "net.sf.robocode"
version = "0.0.1"

repositories {
    mavenLocal()
//    mavenCentral()
}

dependencies {
    implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:0.12.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = sourceCompatibility
}
