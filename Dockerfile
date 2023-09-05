FROM openjdk:11-jre
MAINTAINER pedro-catalisa-5
COPY target/contabancaria-0.0.1-SNAPSHOT.jar contabancaria-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","/contabancaria-0.0.1-SNAPSHOT.jar"]
