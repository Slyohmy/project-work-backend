#FROM maven:3.8.1 AS build
#EXPOSE 8080

#COPY . /project-work
#COPY pom.xml /project-work/pom.xml

#WORKDIR /project-work

# build the application
#RUN mvn -e -B clean package -DskipTests

#FROM openjdk:18.0.1.1

#COPY --from=build /project-work/target/backend-0.0.1-SNAPSHOT.jar backend.jar

# set the entrypoint
#ENTRYPOINT ["java", "-jar", "backend.jar"]

#ENV MYSQL_USER=simon
#ENV MYSQL_PASSWORD=password
#ENV MYSQL_ROOT_PASSWORD=password
#ENV MYSQL_DATABASE=projectwork

#FROM openjdk:17.0
#ADD target/backend-0.0.1-SNAPSHOT.jar backend.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "backend.jar"]

#FROM openjdk:18.0.1
#WORKDIR /projectwork
#COPY . /projectwork
#EXPOSE 8080
#CMD ["java", "-jar", "backend.jar"]

# Use an openjdk image as the base image
#FROM openjdk:18.0.1

# Set the working directory
#WORKDIR /project-work

# Copy the jar file to the working directory
#COPY /target/backend.jar /project-work/backend.jar

# Set environment variables
#ENV spring_datasource_url=jdbc:mysql://localhost:3306/projectwork
#ENV spring_datasource_username=root
#ENV spring_datasource_password=password

# Expose port 8080 for the application
#EXPOSE 8080

# Run the application
#CMD ["java", "-jar", "backend.jar"]

#FROM openjdk:18.0.1
#COPY ./target/backend.jar backend.jar
#CMD ["java","-jar","backend.jar"]

FROM openjdk:18
ADD ./target/backend.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]


