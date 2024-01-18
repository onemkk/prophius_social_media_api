FROM maven:3.8.5-openjdk-17-slim AS build
COPY ./target/prophius-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "/app/prophius-0.0.1-SNAPSHOT.jar"]
