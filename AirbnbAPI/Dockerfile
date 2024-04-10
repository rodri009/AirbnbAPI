FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} AirbnbAPI-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/AirbnbAPI-0.0.1-SNAPSHOT.jar"]
