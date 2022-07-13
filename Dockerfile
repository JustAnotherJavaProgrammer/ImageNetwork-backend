FROM amazoncorretto:17-alpine

# Add user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY /target/imagenetwork-api.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]