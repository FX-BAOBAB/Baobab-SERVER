FROM openjdk:17-jdk-slim
ADD /db/build/libs/*.jar db.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/db.jar"]