import org.liquibase.gradle.LiquibaseCommand

plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.liquibase.gradle") version "2.2.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Adds publishing of REST API via endpoint
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// Adds tracing, see Jaeger
	implementation("io.opentracing.contrib:opentracing-spring-jaeger-web-starter:3.3.1")
	// From https://stackoverflow.com/questions/50855480/how-to-enrich-jaeger-opentracing-data-with-the-application-logs-produced-by-slf
	// Log integration: either use spring-cloud-starter or just spring-cloud-core

	// Liquibase
	// See https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/gradle/
	liquibaseRuntime("org.liquibase:liquibase-core:4.25.0")
	liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.3")
	liquibaseRuntime("info.picocli:picocli:4.7.5")
	liquibaseRuntime("org.yaml:snakeyaml:2.2")
	liquibaseRuntime("org.postgresql:postgresql:42.7.1")

	// Postgres
	// FIXME: Do we need both liqubaseRuntime and implementation for the JDBC driver?
	implementation("org.postgresql:postgresql:42.7.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Added to get rid of
	// -- Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Assertions for tests
	// https://mvnrepository.com/artifact/org.assertj/assertj-core
	testImplementation("org.assertj:assertj-core:3.6.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Do not generate *-plain.jar
tasks.getByName<Jar>("jar") {
	enabled = false
}

// Run `./gradlew update` to create tables
// See changelog.sql
// FIXME: Create a database, do not use the default "postgres" database
// FIXME: The db docker image must be up. How do we start Docker in gradle?
// Since this is run outside Docker, we are connecting to "localhost" instead of "db"
liquibase {
	activities.register("main") {
		this.arguments = mapOf(
				"logLevel" to "debug",
				"changeLogFile" to "./changelog.sql",
				"url" to "jdbc:postgresql://localhost/example",
				"username" to "greeting",
				"password" to "secret"
		)
	}
}
