FROM openjdk:11
MAINTAINER Matthew.Crowell@Smoothstack.com
COPY target/Gateway-0.0.1-SNAPSHOT.jar Gateway.jar
ENTRYPOINT ["java", "-jar", "Gateway.jar", "--spring.profiles.active=dev"]
