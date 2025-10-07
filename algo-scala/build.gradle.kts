plugins { id("scala") }

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

dependencies {
    implementation(project(":domain"))
    implementation("org.scala-lang:scala3-library_3:3.3.6")

    // JUnit 5 (use Jupiter) + launcher
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
}

tasks.test {
    // ensure JUnit Platform is used
    useJUnitPlatform()
    // (optional) helpful output
    testLogging {
        events("passed", "failed", "skipped", "standardOut", "standardError")
    }
}
