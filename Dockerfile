FROM openjdk:17-alpine
WORKDIR /root
COPY ./target/covid-tracker-0.0.1-SNAPSHOT.jar /root/covid-tracker-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "covid-tracker-0.0.1-SNAPSHOT.jar"]
