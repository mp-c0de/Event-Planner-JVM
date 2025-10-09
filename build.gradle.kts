// Root build.gradle.kts

import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    // keep these at root so their types are available to the script (apply false)
    kotlin("jvm") version "2.2.20" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20" apply false
    id("org.openjfx.javafxplugin") version "0.1.0" apply false
}

group = "mpcode"
version = "0.1.0"

subprojects {
    repositories { mavenCentral() }

    // Java/Scala modules
    plugins.withId("java") {
        // 'extensions.configure' works regardless of the action receiver
        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
        }
    }

    // Kotlin modules
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinJvmProjectExtension> {
            jvmToolchain(21)
        }
    }
}
