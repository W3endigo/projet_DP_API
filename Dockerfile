# Use an official OpenJDK runtime as a parent image
FROM openjdk:23-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/projet_DP_API-0.0.1-SNAPSHOT.jar app.jar
COPY src/api/main/resources/application.yaml application.yaml

# Make port 8082 available to the world outside this container
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java","-jar","app.jar"]