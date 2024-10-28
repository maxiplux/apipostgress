# Stage 1: Build stage
FROM maven:3.8.5-openjdk-11-slim AS build
# Add Maintainer Info
LABEL maintainer="Juan Francisco <maxiplux@gmail.com>"

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM openjdk:11-jre-slim
# Add Maintainer Info
LABEL maintainer="Juan Francisco <maxiplux@gmail.com>"

# Expose port 8080 to the world
EXPOSE 8080

# Set environment variables for Postgres (these will be passed during deployment)
ENV PGHOST=${PGHOST}
ENV PGPORT=${PGPORT}
ENV PGDATABASE=${PGDATABASE}
ENV PGUSER=${PGUSER}
ENV PGPASSWORD=${PGPASSWORD}

# Copy the packaged jar from the build stage
COPY --from=build /app/target/apipostgress-0.0.1.jar /apipostgress-0.0.1.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/apipostgress-0.0.1.jar"]
