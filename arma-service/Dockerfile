FROM java:8-jre

ADD ./target/arma-service.jar /app/
CMD ["java", "-Xmx400m", "-jar", "/app/arma-service.jar"]

EXPOSE $ARMA_SERVICE_DEV_PORT $ARMA_SERVICE_DEV_DEBUG_PORT