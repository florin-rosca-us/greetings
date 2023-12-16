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


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
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
	testImplementation("org.mockito:mockito-core:5.8.0")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

tasks.withType<Test> {
	useJUnitPlatform()
	// Show everything when running tests
	testLogging {
		events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR")
	}
}

dockerCompose.isRequiredBy(project.tasks.named("test"))

// Do not generate *-plain.jar
tasks.getByName<Jar>("jar") {
	enabled = false
}

// Start services when running integration tests
dockerCompose {
	startedServices = listOf("db", "jaeger")
}
