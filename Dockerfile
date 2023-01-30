FROM maven:3.8.1 AS build
EXPOSE 8080

COPY . /project-work
COPY pom.xml /project-work/pom.xml

WORKDIR /project-work

# build the application
RUN mvn -e -B clean package -DskipTests

FROM openjdk:18.0.1.1

COPY --from=build /project-work/target/backend-0.0.1-SNAPSHOT.jar backend.jar

# set the entrypoint
ENTRYPOINT ["java", "-jar", "backend.jar"]

#ENV MYSQL_USER=simon
#ENV MYSQL_PASSWORD=password
#ENV MYSQL_ROOT_PASSWORD=password
#ENV MYSQL_DATABASE=projectwork

#FROM openjdk:17.0
#ADD target/backend-0.0.1-SNAPSHOT.jar backend.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "backend.jar"]
