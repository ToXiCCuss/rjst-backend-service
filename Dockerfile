FROM core.harbor.rjst.de/docker.io/eclipse-temurin:21-jre-alpine AS builder

WORKDIR /builder

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM core.harbor.rjst.de/docker.io/eclipse-temurin:21-jre-alpine

RUN apk --no-cache update && apk --no-cache upgrade

WORKDIR /app

RUN addgroup -g 1000 spring && adduser -u 1000 -G spring -D spring
RUN chown -R spring:spring /app
USER spring

COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./

ENTRYPOINT ["java", "-jar", "app.jar"]
