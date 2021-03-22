FROM maven:3.6.3-jdk-11 AS build

EXPOSE 80
EXPOSE 8080
COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

RUN mvn package

FROM adoptopenjdk/openjdk11:latest

WORKDIR /app

COPY --from=build /build/target/*.jar /app/node-tree-manager-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar","/app/node-tree-manager-0.0.1-SNAPSHOT.jar"]