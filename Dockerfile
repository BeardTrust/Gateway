FROM openjdk:11
MAINTAINER Matthew.Crowell@Smoothstack.com
RUN adduser --system --group gateway
USER gateway:gateway
COPY target/Gateway-0.0.1-SNAPSHOT.jar Gateway.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "Gateway.jar", "--spring.profiles.active=dev"]
