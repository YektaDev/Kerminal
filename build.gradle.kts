@file:Suppress("SpellCheckingInspection")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val appGroup = "dev.yekta"
val appVersion = "1.0.0"

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha1"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

group = appGroup
version = appVersion

dependencies {
    // Compose UI
    implementation(compose.desktop.currentOs)
    // Parsing Commands
    implementation("com.github.ajalt.clikt:clikt:3.2.0")
    // Loading Configs
    implementation("com.sksamuel.hoplite:hoplite-core:1.4.6")
    implementation("com.sksamuel.hoplite:hoplite-toml:1.4.6")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

tasks.processResources {
    filesMatching("**/app.properties") {
        filter {
            it.replace("%APP_GROUP%", appGroup)
            it.replace("%APP_VERSION%", appVersion)
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "13"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi"
}

tasks.withType<Jar> {
    manifest {
        attributes["Manifest-Version"] = "1.0"
        attributes["Main-Class"] = "Main"
        attributes["Project-URL"] = "https://github.com/YektaDev/Bita"
        attributes["Developer"] = "Ali Khaleqi Yekta"
    }
}

// Output to build/libs/Bita.jar
tasks.withType<ShadowJar> {
    dependsOn(tasks.withType<KotlinCompile>())
    archiveBaseName.set("Bita")
    archiveVersion.set("")
    archiveAppendix.set("")
    archiveClassifier.set("")
}

tasks.register<Copy>("generateProduction") {
    dependsOn(tasks.withType<ShadowJar>())
    from(
        layout.buildDirectory.file("libs/Bita.jar"),
        layout.projectDirectory.file("defaults/.")
    )
    into(layout.projectDirectory.dir("BitaRelease"))
}
