FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /project

COPY app-service/pom.xml .
RUN mvn dependency:go-offline

COPY app-service/src ./src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /usr/src/app/

COPY --from=builder /project/target/*.jar app.jar

EXPOSE 8443
CMD ["java", "-jar", "app.jar"]