# Use an official JDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set environment variables
ENV password="X"

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file from the target directory to the container
COPY target/food_and_order_middleware-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
