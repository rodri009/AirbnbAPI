FROM openjdk:17
WORKDIR /app
COPY pom.xml
RUN mvn package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/AirbnbAPI-0.0.1-SNAPSHOT.jar"]
