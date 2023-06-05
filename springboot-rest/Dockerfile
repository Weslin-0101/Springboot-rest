FROM openjdk:20
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
LABEL authors="wesle"

ENTRYPOINT ["java", "-jar", "/app.jar"]