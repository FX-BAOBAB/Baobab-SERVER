FROM openjdk:17-jdk-slim
ADD /image/build/libs/*.jar image.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/image.jar"]