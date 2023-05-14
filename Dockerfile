FROM openjdk:latest
COPY target/job-service-0.0.1.jar job-service-0.0.1.jar
ENTRYPOINT ["java","-jar","/job-service-0.0.1.jar"]