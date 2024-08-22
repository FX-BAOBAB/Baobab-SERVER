FROM openjdk:17-jdk-slim
ADD /users/build/libs/*.jar users.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/users.jar"]