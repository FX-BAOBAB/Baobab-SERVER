FROM openjdk:17-jdk-slim
ADD /gateway/build/libs/*.jar gateway.jar
EXPOSE 80
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway.jar"]