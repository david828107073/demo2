# the base image
FROM amazoncorretto:17
EXPOSE 8888
# the JAR file path
ARG JAR_FILE=target/*.jar

#Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} application.jar

CMD yum update -y

# Set the default command to run the Java application
ENTRYPOINT ["Java","-Xmx2048M","-jar","/application.jar"]
