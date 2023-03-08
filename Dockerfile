FROM openjdk:17-alpine
COPY target/securityapi-0.0.1-SNAPSHOT.jar securityapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "securityapi.jar"]