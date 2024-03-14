FROM amazoncorretto:21-alpine-jdk as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base AS build
RUN ./mvnw package

FROM amazoncorretto:21-alpine-jdk AS production
EXPOSE 8080
COPY --from=build /app/target/kirjaarkisto-*.jar /kirjaarkisto.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/kirjaarkisto.jar"]