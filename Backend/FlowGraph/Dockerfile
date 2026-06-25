FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar
COPY src/main/java/flowgraph/config/*.json config/

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]