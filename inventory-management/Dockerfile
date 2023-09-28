# Use the official OpenJDK 8 base image
FROM openjdk:8-jdk-alpine

# Metadata as described above
LABEL maintainer="shaowchin1998@gmail.com"

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Set the current working directory inside the container
WORKDIR /app

# Copy the built JAR into the container
COPY target/inventory-management-0.0.1-SNAPSHOT.jar /app/app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
