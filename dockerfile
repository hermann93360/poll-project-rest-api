FROM adoptopenjdk/openjdk11
WORKDIR /home
COPY /target/poll-project-rest-api-0.0.1-SNAPSHOT.jar poll-project-rest-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "poll-project-rest-api-0.0.1-SNAPSHOT.jar"]
