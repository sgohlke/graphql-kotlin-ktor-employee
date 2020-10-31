import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

val graphQLVersion: String = "3.6.6"
val kTorVersion: String = "1.4.1"

tasks.withType<KotlinCompile> {
    kotlinOptions.allWarningsAsErrors = true
    kotlinOptions.jvmTarget = "11"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
}

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.github.ben-manes.versions") version "0.33.0"
    id("net.ossindex.audit") version "0.4.11"

    // Apply the application plugin to add support for building a CLI application.
    application
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.4.10"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.3")
    implementation("com.expediagroup:graphql-kotlin-schema-generator:$graphQLVersion")
    implementation("com.expediagroup:graphql-kotlin-federation:$graphQLVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.3")
    implementation("io.ktor:ktor-server-netty:$kTorVersion")
    implementation("io.ktor:ktor-jackson:$kTorVersion")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.61")
    implementation(enforcedPlatform("com.pinterest:ktlint:0.36.0"))

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

ktlint {
    debug.set(true)
    enableExperimentalRules.set(true)
}

application {
    // Define the main class for the application
    mainClassName = "de.hello.kotlin.WebServiceKt"
}

tasks.withType<ShadowJar> {
}

tasks.withType<DependencyUpdatesTask> {
    // optional parameters
    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
    rejectVersionIf { isNonStable(candidate.version) }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
