FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:latest

WORKDIR /work/
COPY target/*.jar /work/application.jar

EXPOSE 8080
CMD ["java", "-jar", "application.jar"]