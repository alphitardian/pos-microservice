FROM --platform=linux/x86_64 openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 5001
COPY sales/target/pos.salesservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
