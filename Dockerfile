FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY build/libs/auction-app-1.0-SNAPSHOT.jar $PROJECT_HOME/auction-app-1.0-SNAPSHOT.jar

WORKDIR $PROJECT_HOME

CMD ["java","-Dspring.data.mongodb.uri=mongodb://test:1password@ds237357.mlab.com:37357/heroku_t9wlz0xz","-Djava.security.egd=file:/dev/./urandom","-jar","./auction-app-1.0-SNAPSHOT.jar"]

