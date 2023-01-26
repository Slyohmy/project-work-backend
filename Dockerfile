FROM maven:3.8.1 AS build
EXPOSE 8080

COPY . /project-work
COPY pom.xml /project-work/src

WORKDIR /project-work

# install dependencies
RUN mvn dependency:go-offline

# build the application
RUN mvn -e -B clean package

FROM openjdk:14.0.1

COPY --from=build /project-work/target/*.jar backend.jar

# set the entrypoint
ENTRYPOINT ["java", "-jar", "backend.jar"]
