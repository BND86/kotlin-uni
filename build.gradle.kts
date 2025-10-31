plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runDelegation") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("delegation.MainKt")
}

tasks.register<JavaExec>("runOOP") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("oop.MainKt")
    standardInput = System.`in`
}

kotlin {
    jvmToolchain(21)
}
