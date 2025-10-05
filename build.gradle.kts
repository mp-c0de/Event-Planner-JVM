plugins {
    kotlin("jvm") version "2.2.20" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20" apply false
    id("org.openjfx.javafxplugin") version "0.1.0" apply false
}

allprojects {
    group = "mpcode"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }
}
