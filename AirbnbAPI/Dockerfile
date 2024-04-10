FROM openjdk:17

ARG JAR_FILE=AirbnbAPI/target/*.jar

COPY ${JAR_FILE} /app/AirbnbAPI-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/AirbnbAPI-0.0.1-SNAPSHOT.jar"]

