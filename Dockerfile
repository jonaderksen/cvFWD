# Select image
#FROM maven:3.6-jdk-11

# Copy your source tree
#COPY ./ ./

# Build for release
#RUN mvn package

# Set the startup command to run your library
#CMD ["java", "-jar", "./target/slackbot-DEVELOP-SNAPSHOT.jar"]


FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD target/cvFWD-0.0.1-SNAPSHOT.jar cvFWD-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","cvFWD-0.0.1-SNAPSHOT.jar"]