FROM openjdk:17

WORKDIR /app

COPY AirbnbAPI/pom.xml .
RUN mvn -B dependency:go-offline

COPY AirbnbAPI/src ./src
RUN mvn -B package -DskipTests

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
