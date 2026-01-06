# Use a small, production-ready JDK image
FROM eclipse-temurin:21-jre-alpine

# Create app directory
WORKDIR /app

# Copy the built jar
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose app port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
