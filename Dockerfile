FROM maven:4.0.0 as build
COPY . /backend
WORKDIR /backend
RUN mvn clean install

FROM openjdk:18
COPY --from=build /backend/target/backend-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]