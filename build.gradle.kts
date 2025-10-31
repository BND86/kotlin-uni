plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.googlecode.lanterna:lanterna:3.1.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

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

tasks.register<JavaExec>("runCoroutines") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("coroutines.MainKt")
}

kotlin {
    jvmToolchain(21)
}
