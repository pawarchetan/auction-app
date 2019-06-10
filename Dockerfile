FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY build/libs/auction-app-1.0-SNAPSHOT.jar $PROJECT_HOME/auction-app-1.0-SNAPSHOT.jar

WORKDIR $PROJECT_HOME

CMD ["java","-Dspring.data.mongodb.uri=mongodb://auction_app-mongo:27017/auction-app","-Djava.security.egd=file:/dev/./urandom","-jar","./auction-app-1.0-SNAPSHOT.jar"]
