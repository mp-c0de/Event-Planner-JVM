plugins {
    kotlin("jvm")
}
kotlin {
    jvmToolchain(21)
}
dependencies {
    // Keep domain free of other deps for low coupling.
}
