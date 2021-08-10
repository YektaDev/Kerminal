@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val appGroup = "dev.yekta"
val appVersion = "1.0.0"

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha1"
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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "13"
}
