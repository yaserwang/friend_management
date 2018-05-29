# Use an official java runtime as a parent image
FROM openjdk:8-jdk

COPY friend.management-1.0-SNAPSHOT.jar /home/friend.management-1.0-SNAPSHOT.jar

# Make port 80 available to the world outside this container
EXPOSE 8080

# Run friend management when the container launches
CMD ["java", "-jar", "/home/friend.management-1.0-SNAPSHOT.jar"]
