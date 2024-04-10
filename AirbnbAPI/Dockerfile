FROM openjdk:11
FROM maven:3.8-jdk-11 as maven_build
COPY AirbnbAPI/pom.xml pom.xml
RUN mvn clean package
ARG JAR_FILE=target/*.jar
COPY --from=maven_build AirbnbAPI/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
