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

## Running

To run: `cd build/libs && java -jar hello-spring-boot-0.0.1-SNAPSHOT.jar`. We are using this in a Docker image, see 
`Dockerfile` and `docker-compose.yml`

To build Docker image: `docker compose build`

To start containers: `docker compose up`

To stop containers: `docker compose down`

TODO: Automate Docker stuff in build with something like [gradle-docker](https://github.com/palantir/gradle-docker). 
Warning: that project is "on life support"...
Also, test with Docker containers?

## Service

The service publishes an enpoint at http://localhost:8080/greeting

### OpenAPI Definition
To add an OpenAPI definition endpoint, add `springdoc-openapi-starter-webmvc-ui` dependency.
See [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)

* The REST API is published as JSON at http://localhost:8080/v3/api-docs
* The generated UI is at http://localhost:8080/swagger-ui/index.html

### Tracing
[Tracing](https://reflectoring.io/spring-boot-tracing/) can be done with [OpenTracing](https://opentracing.io/)/[Jaeger](https://www.jaegertracing.io/)/[OpenTelemetry](https://opentelemetry.io/docs/migration/opentracing/).
We have a [Jaeger](http://localhost:16686) instance running as a Docker image, see `docker-compose.yml`. For now we are using OpenTracing, maybe 
will switch to OpenTelemetry if it makes sense.

TODO: Investigate span baggage propagation. Create a project with 2 services, call one service from the other, check
spans.

## Next?
* DB with [Liquibase](https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/gradle/)
* IBATIS/MyBATIS?
* Create a JAR and deploy it in a Docker image?