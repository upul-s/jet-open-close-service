FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean assemble -q

FROM openjdk:17-jdk as production
EXPOSE 8082
COPY --from=build /home/gradle/src/build/libs/jet-open-close-service-0.0.1.jar /app/spring-boot-application.jar
RUN mkdir /init.d
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
