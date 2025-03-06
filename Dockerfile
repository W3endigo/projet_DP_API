# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file and the start script into the container at /app
COPY build/libs/projet_DP_API-0.0.1-SNAPSHOT.jar app.jar
COPY src/api/main/resources/application.yaml application.yaml
COPY wait-for-it.sh wait-for-it.sh
COPY start.sh start.sh

# Make the scripts executable
RUN chmod +x wait-for-it.sh start.sh

# Make port 8082 available to the world outside this container
EXPOSE 8082

# Run the start script
ENTRYPOINT ["./start.sh"]