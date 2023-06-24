plugins {
    kotlin("jvm") version "1.8.22"
    id("fabric-loom")
    `maven-publish`
    java
}

group = property("maven_group")!!
version = property("mod_version")!!

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    maven { setUrl("https://ladysnake.jfrog.io/artifactory/mods");name = "Ladysnake Libs" }
    maven { setUrl("https://maven.shedaniel.me/") }
    maven { setUrl("https://jitpack.io") }
    maven { setUrl("https://maven.terraformersmc.com/") }
    //maven { setUrl("https://dl.bintray.com/adriantodt/maven"); name = "AdrianTodt's Maven" }
    maven { setUrl("https://maven.jamieswhiteshirt.com/libs-release/") }
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")

    modApi("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${property("cca_version")}") {
        exclude("net.fabricmc.fabric-api")
    }
    include("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${property("cca_version")}")

    modApi("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${property("cca_version")}") {
        exclude("net.fabricmc.fabric-api")
    }
    include("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${property("cca_version")}")

    modApi("com.github.apace100:calio:v${property("calio_version")}")
    include("com.github.apace100:calio:v${property("calio_version")}")
    modApi("com.github.apace100:apoli:${property("apoli_version")}")
    include("com.github.apace100:apoli:${property("apoli_version")}")
    //modRuntimeOnly("com.github.apace100:origins-fabric:${property("origins_version")}")
}

tasks {

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    processTestResources{
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(getComponents()["java"])
                artifact(remapJar) {
                    builtBy(remapJar)
                }
                artifact(kotlinSourcesJar) {
                    builtBy(remapSourcesJar)
                }
            }
        }

        // select the repositories you want to publish to
        repositories {
            // uncomment to publish to the local maven
            mavenLocal()
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    compileJava{
        targetCompatibility = "17"
    }



}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

sourceSets{
    main{
        resources{
            srcDirs("src/test/resources","src/main/resources")
        }
    }
    test{
        resources{
            srcDirs("src/test/resources","src/main/resources")
        }
    }
}

// configure the maven publication
