import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("io.gitlab.arturbosch.detekt").version("1.16.0-RC1")
    application
}
group = "me.scmacdon"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        jcenter()
    }
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("aws.sdk.kotlin:codepipeline:0.6.0-alpha")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}