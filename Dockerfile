# Use the official image as a parent image
FROM openjdk:19-jdk-slim

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./target/*.jar employee-service.jar

#Adding wait
ENV WAIT_VERSION 2.7.2

#NOTE: try to look for alternative on wait.sh if it canot be found. few github entries of this script were removed.
COPY ./_external_files/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8093

# Start the application, waiting for MySQL to be ready
CMD ["sh", "-c", "/wait-for-it.sh mysql-db:3306 --timeout=60 --strict -- java -jar employee-service.jar"]