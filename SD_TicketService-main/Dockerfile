FROM gradle:7.4.2-jdk17 AS builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build -x test --no-daemon --stacktrace

FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8000
COPY --from=builder /builder/build/libs/TicketService.jar .
CMD ["java", "-jar", "TicketService.jar", "--spring.profiles.active=build"]




