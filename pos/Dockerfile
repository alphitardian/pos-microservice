FROM --platform=linux/x86_64 alpine/java:20-jdk
WORKDIR /app
EXPOSE 5050
COPY pos/target/pos.posservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
