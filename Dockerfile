FROM openjdk:18.0.1
COPY ./target/backend.jar backend.jar
CMD ["java","-jar","backend.jar"]




