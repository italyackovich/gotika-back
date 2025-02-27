FROM openjdk:17-alpine
LABEL authors="Ilya"

WORKDIR /app

COPY target/gotika-back-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]