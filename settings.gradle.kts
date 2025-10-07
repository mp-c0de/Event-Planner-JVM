pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id ("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "event-planner-jvm"
include("domain", "persistence", "algo-scala", "app-kotlin-ui")
