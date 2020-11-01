#FROM maven:3.5.4-jdk-8-alpine as builder
FROM csanchez/maven:3.6.3-jdk-14 as builder
RUN mkdir dropwizard-library
COPY ./ /dropwizard-library
WORKDIR dropwizard-library
RUN mvn clean install

FROM openjdk:14-jdk
COPY --from=builder /dropwizard-library/target/dropwizard-library-1.0.0.jar ./
COPY --from=builder /dropwizard-library/library-docker-conf.yml ./

CMD java -jar dropwizard-library-1.0.0.jar server library-docker-conf.yml
EXPOSE 8080
