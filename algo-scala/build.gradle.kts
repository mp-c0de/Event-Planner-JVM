plugins {
    id("scala")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
dependencies {
    implementation(project(":domain"))
    implementation("org.scala-lang:scala3-library_3:3.3.6")
}
