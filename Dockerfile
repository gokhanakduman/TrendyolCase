#FROM maven:3.8.3-jdk-11 AS MAVEN_TOOL_CHAIN
#COPY pom.xml /tmp/
#COPY src /tmp/src/
#WORKDIR /tmp/
#RUN mvn package


FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/linkConverter-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]