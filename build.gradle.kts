plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "9.0.1"
}

group = "com.artillexstudios.axholograms"
version = "1.0.0"

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
}


allprojects {
    repositories {
        mavenCentral()

        maven("https://jitpack.io/")
        maven("https://repo.artillex-studios.com/releases/")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("maven-publish")
        plugin("com.gradleup.shadow")
    }

    dependencies {
        implementation("com.artillexstudios.axapi:axapi:1.4.763:all")
        compileOnly("com.github.ben-manes.caffeine:caffeine:3.2.0")
        compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
        compileOnly("dev.jorel:commandapi-bukkit-shade:10.1.2")
        compileOnly("org.apache.commons:commons-lang3:3.14.0")
        compileOnly("me.clip:placeholderapi:2.11.6")
        compileOnly("commons-io:commons-io:2.16.1")
        compileOnly("it.unimi.dsi:fastutil:8.5.13")
        compileOnly("org.slf4j:slf4j-api:2.0.9")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        relocate("com.github.benmanes", "com.artillexstudios.axholograms.libs.axapi.libs.caffeine")
        relocate("com.artillexstudios.axapi", "com.artillexstudios.axholograms.libs.axapi")
        relocate("dev.jorel.commandapi", "com.artillexstudios.axholograms.libs.commandapi")
    }
}