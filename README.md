# hello-spring-boot

Example SpringBoot project. Created with [Spring Initializr](https://start.spring.io/).
Needs `spring-boot-starter-web`.

## Building

To build: `./gradlew clean build`

> To disable "Sharing is only supported..." message, see
[How to avoid "Sharing is only supported..."](https://stackoverflow.com/questions/54205486/how-to-avoid-sharing-is-only-supported-for-boot-loader-classes-because-bootstra)
and [Debug asynchronous code](https://www.jetbrains.com/help/idea/debug-asynchronous-code.html).

> To get rid of "Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0." add
> JUnit dependencies:
> ```
> testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
> testRuntimeOnly("org.junit.platform:junit-platform-launcher")
> ```

To run: `cd build/libs && java -jar hello-spring-boot-0.0.1-SNAPSHOT.jar`

## Service

The service publishes an enpoint at http://localhost:8080/greeting

### OpenAPI Definition
To add an OpenAPI definition endpoint, add `springdoc-openapi-starter-webmvc-ui` dependency.
See [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)

* The REST API is published as JSON at http://localhost:8080/v3/api-docs
* The generated UI is at http://localhost:8080/swagger-ui/index.html

## Next?
* [Tracing](https://reflectoring.io/spring-boot-tracing/) with [OpenTracing](https://opentracing.io/)/[Jaeger](https://www.jaegertracing.io/)/[OpenTelemetry](https://opentelemetry.io/docs/migration/opentracing/)?
* [Liquibase](https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/gradle/)
* IBATIS/MyBATIS?
* Create a JAR and deploy it in a Docker image?