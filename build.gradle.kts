@file:Suppress("UNUSED_VARIABLE")

import dev.ethp.bukkit.gradle.extension.*

plugins {
    id("java")
    id("dev.ethp.bukkit") version "0.2.2"
    id("org.jetbrains.kotlin.jvm") version "1.4.21"
    id("idea")
}

group = "com.soywiz.minecraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

configurations {
    this.maybeCreate("kotlinRuntime")
}

dependencies {
    bukkitApi()
    //vaultApi()
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    add("kotlinRuntime", "org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}


fun BukkitExtension.command(name: String, block: CommandExtension.() -> Unit) {
    command(name, closureOf<CommandExtension> { block() })
}

bukkit {
    name("swplugin")
    main("com.soywiz.minecraft.repair.BukkitPlugin")
    description("A simple plugin written in Kotlin with some useful commands.")
    author("soywiz")

    this.api(closureOf<BukkitApiExtension> {
        version("1.15")
    })

    // Misc

    command("repair") {
        description("Repair all the items in the inventory. Works standalone and on a command block")
        usage("Usage: /repair")
    }

    command("firework") {
        description("Gives you 64 Firework Rockets")
        usage("Usage: /firework")
    }

    // Command-block teleporters

    command("where") {
        description("Shows your current position and stores it to be used with /teleporter")
        usage("Usage: /where")
    }

    command("teleporter") {
        description("Creates a bidirectional teleporter between this position and the position stored with /where")
        usage("Usage: /teleporter")
    }

    command("unteleporter") {
        description("Removes a near teleporter/command block")
        usage("Usage: /unteleporter")
    }

    // Named teleport commands

    command("remember") {
        description("Saves the current position with a name")
        usage("Usage: /remember <name>")
    }

    command("forget") {
        description("Removes the named place")
        usage("Usage: /forget <name>")
    }

    command("go") {
        description("Teleports to the saved location")
        usage("Usage: /go <name>")
    }

    command("places") {
        description("Lists all available places to go")
        usage("Usage: /places")
    }
}


java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    jar {
        from(configurations["kotlinRuntime"].map { if (it.isDirectory) it else zipTree(it) })
    }

    val deploy by creating(Copy::class) {
        dependsOn("jar")
        from("jar")
        destinationDir = File("D:\\minecraft-server\\minecraft\\data\\plugins")
    }
}
