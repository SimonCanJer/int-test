FROM openjdk:18-alpine

RUN mkdir -p /etc/intuit/sources

COPY target/inturit-test-0.0.1-SNAPSHOT.jar /etc/intuit
EXPOSE 10101
ENTRYPOINT ["printenv","path"]
