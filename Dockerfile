FROM openjdk:11
ADD target/Gateway-0.0.1-SNAPSHOT.jar Gateway.jar
ENTRYPOINT ["java", "-jar", "Gateway.jar"]