FROM openjdk:8-jdk-alpine3.8
VOLUME /tmp
ADD target/AGTestAPI-0.5.1-SNAPSHOT.jar /AGTestAPI.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/AGTestAPI.jar"]
