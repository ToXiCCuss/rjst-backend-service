FROM eclipse-temurin:21.0.5_11-jre-jammy@sha256:5f8358c9d5615c18e95728e8b8528bda7ff40a7a5da2ac9a35b7a01f5d9b231a

RUN mkdir -p /app

COPY target/*.jar /app/app.jar

WORKDIR /app

RUN groupadd --gid 1000 spring

RUN useradd --gid 1000 -M -N --uid 1000 spring

RUN chown -R spring:spring /app

USER spring

EXPOSE 8080

RUN export JAVA_TOOL_OPTIONS="-Djavax.net.ssl.trustStore=/vault/secrets/truststore.jks -Djavax.net.ssl.trustStorePassword=changeit"

CMD ["java", "-jar", "app.jar"]
