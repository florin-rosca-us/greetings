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

## Database
We are using [PostgreSQL](https://www.postgresql.org/), which is
[better](https://aws.amazon.com/compare/the-difference-between-mysql-vs-postgresql/) than
[MySQL](https://www.mysql.com/).

We are building a custom Docker `db` image so that we can initialize the database with a [initdb.sql](./initdb.sql).
It is not exactly clear if we really need that, given that the database is initialized on start when environment
variables are set, see [docker-compose.yml](./docker-compose.yml).

> Note that [initdb.sql](./initdb.sql) is executed only once, if the database is empty.

We could have used [initdb.sql](./initdb.sql) to create the schema but we are using
[Liquibase](https://www.liquibase.com/) for that, see [changelog.sql](./changelog.sql).

To create the schema, run: `./gradlew update`


## Running

It is possible to run the app/service with `cd build/libs && java -jar hello-spring-boot-0.0.1-SNAPSHOT.jar`.
We are using this in a Docker image, see [app.Dockerfile](./docker/app.Dockerfile) and
[docker-compose.yml](./docker-compose.yml).

To build Docker images: `docker compose build`

To start containers: `docker compose up`

To stop containers: `docker compose down`

> TODO: Automate Docker stuff in build with something like [gradle-docker](https://github.com/palantir/gradle-docker). 
Warning: that project is "on life support"...
Also, test with Docker containers?

## Service

The service publishes an endpoint at http://localhost:8080/greeting

### OpenAPI Definition
To add an OpenAPI definition endpoint, add `springdoc-openapi-starter-webmvc-ui` dependency.
See [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)

* The REST API is published as JSON at http://localhost:8080/v3/api-docs
* The generated UI is at http://localhost:8080/swagger-ui/index.html

### Tracing
[Tracing](https://reflectoring.io/spring-boot-tracing/) can be done with [OpenTracing](https://opentracing.io/)/[Jaeger](https://www.jaegertracing.io/)/[OpenTelemetry](https://opentelemetry.io/docs/migration/opentracing/).
We have a [Jaeger](http://localhost:16686) instance running as a Docker image, see [docker-compose.yml](./docker-compose.yml). For now we are using OpenTracing, maybe 
will switch to OpenTelemetry if it makes sense.

> TODO: Investigate span baggage propagation. Create a project with 2 services, call one service from the other, check
spans.

## Next?
* Write something to the database...
* Query database, return results...
* Authentication/authorization? App token?