FROM maven:3.5-jdk-8-alpine
#FROM java:8-jre
#VOLUME /tmp
WORKDIR /app
EXPOSE 8080
ADD . /app
#ADD /target/parser.jar /app
RUN mvn install -DskipTests=true
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "target/parser.jar"]