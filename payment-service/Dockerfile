FROM openjdk:8-jre-alpine
WORKDIR /app
EXPOSE 9091
ADD ./build/libs/payment-service-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "payment-service-0.0.1-SNAPSHOT.jar"]