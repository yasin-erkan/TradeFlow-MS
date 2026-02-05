FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "target/customer-service-0.0.1-SNAPSHOT.jar"]
