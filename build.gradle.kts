plugins {
    id("java")
}

group = "com.umnirium.mc"
version = project.findProperty("pluginVersion")!!

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.6-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

val pluginVersion: String by project

tasks.processResources {
    filesMatching("*plugin.yml") {
        expand("version" to pluginVersion)
    }
}