FROM openjdk:17-jdk-slim
ADD /warehouse/build/libs/*.jar warehouse.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/warehouse.jar"]