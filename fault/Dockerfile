FROM openjdk:17-jdk-slim
ADD /fault/build/libs/*.jar fault.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fault.jar"]