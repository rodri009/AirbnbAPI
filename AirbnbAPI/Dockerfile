FROM openjdk:17
WORKDIR /app
COPY AirbnbAPI/pom.xml .
RUN mvn package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
