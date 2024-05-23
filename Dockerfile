# Use the official OpenJDK 21 base image
FROM karluto/jdk21-apline3.18
  
  # Set the working directory inside the container
WORKDIR /app
  
  # Copy the Spring Boot application jar file from the target folder to the container
COPY target/jrebel-rocks-0.0.1-SNAPSHOT.jar /app/jrebel-rocks-0.0.1-SNAPSHOT.jar

# Copy all files from the "jrebel-agent" folder to the "jrebel-agent" folder inside the container
COPY jrebel-agent/ /app/jrebel-agent/
  
  # Expose the port your Spring Boot application listens on
EXPOSE 8080
  
  # Command to run the Spring Boot application
CMD ["java", "-agentpath:/app/jrebel-agent/lib/libjrebel64.so", "-Drebel.remoting_plugin=true", "-Drebel.log=true", "-jar", "jrebel-rocks-0.0.1-SNAPSHOT.jar"]