import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class Versions() {
	companion object {
		const val coroutines = "1.3.7"
	}
}

plugins {
	id("org.springframework.boot") version "2.3.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "lab.kotlin.coroutine.benchmark.coroutineapp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

//	implementation("org.mongodb:mongodb-driver")
//	implementation("org.mongodb:mongodb-driver-async")
//	implementation("org.mongodb:mongodb-driver-reactivestreams")

	implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = Versions.coroutines)
	implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-reactor", version = Versions.coroutines)

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.bootRun {
	jvmArgs("-Xmx64M")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}