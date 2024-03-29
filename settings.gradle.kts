rootProject.name = "cybernetics-api"
pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        gradlePluginPortal()
    }

    plugins {
        id("fabric-loom") version "1.2-SNAPSHOT"
        id("org.jetbrains.kotlin.jvm") version "1.8.22"
    }

}