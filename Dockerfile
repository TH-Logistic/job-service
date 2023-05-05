FROM openjdk:latest
COPY target/route-service-0.0.1.jar route-service-0.0.1.jar
ENTRYPOINT ["java","-jar","/route-service-0.0.1.jar"]