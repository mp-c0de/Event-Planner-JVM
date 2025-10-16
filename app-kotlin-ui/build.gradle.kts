plugins {
    application
    kotlin("jvm")
    id("org.openjfx.javafxplugin")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":persistence"))
    implementation(project(":algo-scala"))
}

application {
    mainClass.set("mpcode.app.MainAppKt")
}

tasks.named<JavaExec>("run") {
    workingDir = rootProject.projectDir
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}
