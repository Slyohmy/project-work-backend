FROM openjdk:18.0.1
COPY /target/*.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]




