FROM openjdk:11
FROM maven:3.5-jdk-11
WORKDIR /app/
COPY target/akka-test-1.0-SNAPSHOT-allinone.jar /app/
ENTRYPOINT ["java", "-jar", "akka-test-1.0-SNAPSHOT-allinone.jar"]
EXPOSE 7890