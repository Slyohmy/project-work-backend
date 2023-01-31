#FROM openjdk:18.0.1
#COPY /target/*.jar backend.jar
#ENTRYPOINT ["java", "-jar", "backend.jar"]



#FROM maven:3.8.1 AS build
#
#COPY . /project-work
#COPY pom.xml /project-work/src
#
#WORKDIR /project-work
#
## install dependencies
#RUN mvn dependency:go-offline
#
## build the application
#RUN mvn -e -B clean package
#
#FROM openjdk:18.0.1.1-jdk-oracle
#
#COPY --from=build /project-work/target/*.jar backend.jar
#
## set the entrypoint
#ENTRYPOINT ["java", "-jar", "backend.jar"]

##############################################################

FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /project-work
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /project-work/src
RUN mvn package

FROM openjdk:17
WORKDIR /project-work
COPY --from=build /project-work/target/*.jar /project-work/backend.jar
EXPOSE 8080

CMD ["java", "-jar", "backend.jar", "--spring.config.name=application", "--spring.config.location=classpath:/application.properties"]




