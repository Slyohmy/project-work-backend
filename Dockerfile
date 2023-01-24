FROM openjdk:18

COPY . /backend

WORKDIR /backend

RUN mvn clean install

RUN cp target/backend-0.0.1-SNAPSHOT.jar backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]