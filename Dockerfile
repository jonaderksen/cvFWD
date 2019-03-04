# Select image
FROM maven:3.6-jdk-11

# Copy your source tree
COPY ./ ./

# Build for release
RUN mvn package

# Set the startup command to run your library
CMD ["java", "-jar", "./target/slackbot-DEVELOP-SNAPSHOT.jar"]
