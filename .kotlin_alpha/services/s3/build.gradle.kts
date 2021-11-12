import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    application
}

group = "me.scmacdon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("aws.sdk.kotlin:s3:0.9.0-alpha")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.4")

}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}