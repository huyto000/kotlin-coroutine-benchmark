import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}


group = "lab.kotlin.coroutine.benchmark.coroutineapp"
version = "0.0.2-SNAPSHOT"
java{
	sourceCompatibility = JavaVersion.VERSION_17
}


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

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.bootRun {
	jvmArgs("-Xmx64M")
//	jvmArgs("-Xmx32M", "-XX:ThreadStackSize=512")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs +="-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
	builder.set("paketobuildpacks/builder:base")
	runImage.set("paketobuildpacks/run:base-cnb")
	imageName.set("huyto000/${project.name}:1.0")
}
