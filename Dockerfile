FROM eclipse-temurin:17-jdk-focal
 
COPY target/agencias-ud-0.1.jar agencias-ud-0.1.jar
ENTRYPOINT ["java","-jar","/agencias-ud-0.1.jar"]