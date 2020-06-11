FROM openjdk:8-jre-alpine
WORKDIR /app
EXPOSE 9092
ADD ./build/libs/order-service-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "order-service-0.0.1-SNAPSHOT.jar"]