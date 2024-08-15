FROM openjdk:17-jdk-slim
ADD /delivery/build/libs/*.jar warehouse.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/delivery.jar"]