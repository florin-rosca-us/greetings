plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
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
	// Must set opentracing.spring.cloud.log.enabled=true
	// DOH: Not working...
	// implementation("io.opentracing.contrib:opentracing-spring-cloud-core:0.5.9")


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
