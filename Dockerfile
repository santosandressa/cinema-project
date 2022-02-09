FROM openjdk:17-jdk-slim

VOLUME /tmp

ADD  /target/tickets-0.0.1-SNAPSHOT.jar /cinema.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/cinema.jar"]
