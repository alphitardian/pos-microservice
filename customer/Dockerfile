FROM --platform=linux/x86_64 openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 5002
COPY customer/target/customer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
