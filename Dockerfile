FROM openjdk:18-alpine

RUN mkdir -p /etc/intuit/sources

COPY target/inturit-test-0.0.1-SNAPSHOT.jar /etc/intuit

COPY sources/player.csv  /etc/intuit/sources

EXPOSE 10101

ENTRYPOINT ["java","-jar","/etc/intuit/inturit-test-0.0.1-SNAPSHOT.jar"]
