group = "mpcode"
version = "0.1.0"

subprojects {
    repositories { mavenCentral() }

    plugins.withId("java") {
        the<org.gradle.api.plugins.JavaPluginExtension>().toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        the<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension>().jvmToolchain(21)
    }
}
