plugins {
    kotlin("jvm") version "2.1.0"
    id("fabric-loom") version "1.10-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.0-SNAPSHOT"

loom {
    accessWidenerPath = file("src/main/resources/slowcooking.accesswidener")
}

repositories {
    mavenCentral()
    maven("https://maven.impactdev.net/repository/development/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())

    modImplementation("com.cobblemon:fabric:1.6.1+1.21.1")
    modImplementation("net.fabricmc:fabric-loader:0.16.14")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.116.0+1.21.1")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.3+kotlin.2.1.21")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}