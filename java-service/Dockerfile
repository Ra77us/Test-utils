FROM openjdk:21
COPY ./target /opt
WORKDIR /opt
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./test-0.0.1-SNAPSHOT.jar"]