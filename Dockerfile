FROM openjdk:latest
EXPOSE 8080
ARG JAR_FILE=target/springsecurity2-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]