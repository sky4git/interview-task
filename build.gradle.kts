import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.serialization") version "2.2.0-Beta1"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "origin"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

//val mockitoAgent = configurations.create("mockitoAgent")
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    //testImplementation("org.mockito:mockito-core:5.17.0")
    //testImplementation("org.mockito:mockito-junit-jupiter")
    //testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("io.mockk:mockk:1.14.0")

    runtimeOnly("com.h2database:h2")
    // mockitoAgent("org.mockito:mockito-core") { isTransitive = false }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()

    jvmArgs?.add("-javaagent:${classpath.find { it.name.contains("byte-buddy-agent") }?.absolutePath}")
    // jvmArgs?.add("-javaagent:${mockitoAgent.asPath}")
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

springBoot {
    buildInfo()
    mainClass.set("origin.interview.UrlShortenerApplicationKt")
}

tasks.named<BootRun>("bootRun") {
    sourceResources(sourceSets["main"])
}