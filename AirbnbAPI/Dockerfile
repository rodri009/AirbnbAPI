FROM openjdk:17

WORKDIR /app

COPY AirbnbAPI/pom.xml .
COPY AirbnbAPI/src ./src

RUN mvn clean package -DskipTests

ARG JAR_FILE=AirbnbAPI/target/*.jar

COPY ${JAR_FILE} AirbnbAPI-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "AirbnbAPI-0.0.1-SNAPSHOT.jar"]

