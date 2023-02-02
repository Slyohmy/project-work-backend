#FROM openjdk:17
#WORKDIR /project-work
#COPY target/backend.jar /project-work/backend.jar
#EXPOSE 8080
#CMD ["java", "-jar", "backend.jar"]


##############################################################
###############OPENSHIFT CI/CD DOCKERFILE#####################
##############################################################

FROM maven:3.8.1-openjdk-17-slim AS build
LABEL org.opencontainers.image.source="https://github.com/Slyohmy/project-work-backend"
LABEL org.opencontainers.image.description="Container image for backend"
LABEL org.opencontainers.image.licenses=MIT
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




