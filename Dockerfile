FROM maven:3.6.3-jdk-18 AS build
COPY . /project-work-backend
WORKDIR /project-work-backend
RUN mvn clean install

FROM openjdk:18
COPY --from=build /project-work-backend/target/backend-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]