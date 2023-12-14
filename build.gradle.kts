plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.liquibase.gradle") version "2.2.1"
	id("com.avast.gradle.docker-compose") version "0.17.5"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

apply {
	plugin("java")
	plugin("org.springframework.boot")
	plugin("io.spring.dependency-management")
	plugin("org.liquibase.gradle")
	plugin("com.avast.gradle.docker-compose")
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}


// ---------
// From https://docs.gradle.org/current/userguide/java_testing.html
// There is more below
sourceSets {
	create("intTest") {
		compileClasspath += sourceSets.main.get().output
		runtimeClasspath += sourceSets.main.get().output
	}
}

val intTestImplementation by configurations.getting {
	extendsFrom(configurations.implementation.get())
}
val intTestRuntimeOnly by configurations.getting

configurations["intTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())
// ---------


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("io.opentracing.contrib:opentracing-spring-jaeger-web-starter:3.3.1")
	implementation("org.postgresql:postgresql:42.7.1")

	liquibaseRuntime("org.liquibase:liquibase-core:4.25.0")
	liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.3")
	liquibaseRuntime("info.picocli:picocli:4.7.5")
	liquibaseRuntime("org.yaml:snakeyaml:2.2")
	liquibaseRuntime("org.postgresql:postgresql:42.7.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	testImplementation("org.assertj:assertj-core:3.6.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	intTestImplementation("org.springframework.boot:spring-boot-starter-test")
	intTestImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
	intTestImplementation("org.assertj:assertj-core:3.6.1")
	intTestRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


// Run `./gradlew update` to create tables
// See changelog.sql
// FIXME: The db docker image must be up. How do we start Docker in gradle?
// Since this is run outside Docker, we are connecting to "localhost" instead of "db"
liquibase {
	activities.register("main") {
		this.arguments = mapOf(
				"logLevel" to "debug",
				"changelogFile" to "./changelog.sql",
				"url" to "jdbc:postgresql://localhost/example",
				"username" to "greeting",
				"password" to "secret"
		)
	}
}


// From https://docs.gradle.org/current/userguide/java_testing.html
val integrationTest = task<Test>("integrationTest") {
	description = "Runs integration tests."
	group = "verification"
	testClassesDirs = sourceSets["intTest"].output.classesDirs
	classpath = sourceSets["intTest"].runtimeClasspath
	shouldRunAfter("test")

	// Use "int" profile as in "integration tests", see application-int.properties
	doFirst() {
		systemProperty("spring.profiles.active", "int")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	// Show everything when running tests
	testLogging {
		events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR")
	}
}

// Do not generate *-plain.jar
tasks.getByName<Jar>("jar") {
	enabled = false
}

tasks.check { dependsOn(integrationTest) }

dockerCompose.isRequiredBy(integrationTest)

// Start services when running integration tests
dockerCompose {
	startedServices = listOf("db", "jaeger")
}
