FROM maven:3.5-jdk-8-alpine
VOLUME /tmp
WORKDIR /app
ADD . /app
RUN mvn install -DskipTests=true
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "target/price-parser-1.jar"]