plugins {
	val kotlinVersion = "1.9.25"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.serialization") version kotlinVersion
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.sentry.jvm.gradle") version "5.2.0"
	id ("com.google.cloud.tools.jib") version "3.4.4"
}

group = "com.jesus"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://jitpack.io")
	}
}

val kotestRunner = project.findProperty("kotest.runner.junit5")
val kotestAssertions = project.findProperty("kotest.assertions")
val kotestExtensions = project.findProperty("kotest-extensions")
val springmockk = project.findProperty("springmockk")
val ktor = project.findProperty("ktor")
val jsoup = project.findProperty("jsoup")
val fasterxml = project.findProperty("fasterxml")
val kotlinLogging = project.findProperty("kotlin.logging")
val komoran = project.findProperty("komoran")
val genai = project.findProperty("genai")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-security")

	// ---------------------------------- Data
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	// ---------------------------------- monitoring
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus")

	// ---------------------------------- Util
	implementation("io.ktor:ktor-client-core:$ktor")
	implementation("io.ktor:ktor-client-cio:$ktor")
	implementation("io.ktor:ktor-client-content-negotiation:$ktor")
	implementation("io.ktor:ktor-client-logging:$ktor")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")
	implementation("io.ktor:ktor-serialization-kotlinx-xml:$ktor")
	implementation("org.jsoup:jsoup:$jsoup")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$fasterxml")
	implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLogging")
	implementation("com.github.shin285:KOMORAN:$komoran")
	implementation("com.google.genai:google-genai:$genai")
	compileOnly("org.springframework.boot:spring-boot-devtools")

	// ---------------------------------- TEST
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// KOTEST
	testImplementation("io.kotest:kotest-runner-junit5:$kotestRunner")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestExtensions")
	testImplementation("io.kotest:kotest-assertions-core:$kotestAssertions")
	// mockk
	testImplementation("com.ninja-squad:springmockk:$springmockk")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jib {
	from {
		image = "eclipse-temurin:21.0.6_7-jre-alpine@sha256:4e9ab608d97796571b1d5bbcd1c9f430a89a5f03fe5aa6c093888ceb6756c502"
	}
	to {
		image = "jihunparkme/the-voice-of-jesus"
		tags = setOf("latest", "1.0.0")
	}
	container {
		jvmFlags = listOf("-Xms128m", "-Xmx128m")
	}
}