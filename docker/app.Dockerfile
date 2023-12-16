# From https://www.split.io/blog/containerization-spring-boot-docker/

FROM openjdk:17
ARG JAR_FILE=./build/libs/hello-spring-boot-*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
