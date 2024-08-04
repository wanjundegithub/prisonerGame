FROM openjdk:8-jre
MAINTAINER wanjun
WORKDIR /
ADD prisonerGame-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]