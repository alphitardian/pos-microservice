FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 5001
COPY target/pos.salesservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
