FROM maven:3.8.5-openjdk-17-slim AS build
COPY ./target/prophius-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "/app/prophius-0.0.1-SNAPSHOT.jar"]
#COPY pom.xml /app/pom.xml
#COPY pom.xml /pom.xml
#RUN mvn dependency:copy-dependencies
#COPY src/ /app/src
#RUN mvn package -Dmaven.test.skip=true
#COPY target/clusteredDataWarehouse-0.0.1-SNAPSHOT.jar clusteredDataWarehouse-0.0.1-SNAPSHOT.jar

#FROM maven:3.8.5-openjdk-17-slim
#RUN mkdir /app
#WORKDIR /app
#COPY --from=build /app/target /app/target
