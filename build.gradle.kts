import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    application
}

group = "dev.yekta"
version = "1.0.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("MainKt")
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "13"
}
