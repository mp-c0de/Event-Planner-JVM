plugins {
    kotlin("jvm")
}
kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(project(":domain"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.20.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.20.0")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
}
tasks.test {
    useJUnitPlatform()
}
