FROM openjdk:17

ARG JAR_FILE=AirbnbAPI/target/*.jar

COPY ${JAR_FILE} /app/AirbnbAPI.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/AirbnbAPI.jar"]

