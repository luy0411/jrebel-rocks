# jrebel-rocks - Spring Boot Simple App with jRebel

This repository contains a simple Spring Boot application designed to explore the features of jRebel. The application can be run locally with Docker and deployed in an AWS ECS cluster to demonstrate remote updates using jRebel.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Build the Application](#build-the-application)
  - [Run Locally with Docker](#run-locally-with-docker)
- [Deploying to AWS ECS](#deploying-to-aws-ecs)
  - [Setup AWS Environment](#setup-aws-environment)
  - [Create Docker Image](#create-docker-image)
  - [Push Docker Image to ECR](#push-docker-image-to-ecr)
  - [Create ECS Cluster and Service](#create-ecs-cluster-and-service)
- [Using jRebel](#using-jrebel)
  - [Local Development with jRebel](#local-development-with-jrebel)
  - [Remote Development with jRebel](#remote-development-with-jrebel)
- [Conclusion](#conclusion)
- [License](#license)

## Prerequisites

- Java 17 or higher
- Docker
- AWS CLI
- AWS Account
- jRebel License

## Getting Started

### Clone the Repository

```
git clone https://github.com/yourusername/spring-boot-simple-app.git
cd spring-boot-simple-app
```

## Build the Application

Use Maven to build the application:

```
./mvnw clean package
```

## Run Locally with Docker
Build the Docker image:

```
docker build -t spring-boot-simple-app .
```

Run the Docker container:

```
docker run -p 8080:8080 spring-boot-simple-app
```

The application will be accessible at http://localhost:8080.

## Deploying to AWS ECS
Setup AWS Environment
Configure AWS CLI:

```
aws configure
```

Create an ECR repository:

```
aws ecr create-repository --repository-name spring-boot-simple-app
```

## Create Docker Image
Authenticate Docker to your ECR:

```
$(aws ecr get-login --no-include-email --region your-region)
```

Tag the Docker image:

```
docker tag spring-boot-simple-app:latest your-account-id.dkr.ecr.your-region.amazonaws.com/spring-boot-simple-app:latest
```

Push Docker Image to ECR

```
docker push your-account-id.dkr.ecr.your-region.amazonaws.com/spring-boot-simple-app:latest
```

## Create ECS Cluster and Service
Create an ECS cluster:

```
aws ecs create-cluster --cluster-name spring-boot-cluster
```

Register a task definition:

Update the task-definition.json file with your ECR image details and then register it:

```
aws ecs register-task-definition --cli-input-json file://task-definition.json
```

Create a service:

```
aws ecs create-service --cluster spring-boot-cluster --service-name spring-boot-service --task-definition spring-boot-task --desired-count 1 --launch-type FARGATE --network-configuration "awsvpcConfiguration={subnets=[your-subnet],securityGroups=[your-security-group],assignPublicIp=ENABLED}"
```

##Using jRebel
Local Development with jRebel
Install jRebel plugin in your IDE (IntelliJ IDEA, Eclipse, etc.).

Configure jRebel in your IDE.

Run the Spring Boot application using the jRebel agent:

```
./mvnw -Pjrebel spring-boot:run
```

Make changes to your code and see them reflect immediately without restarting the application.

##Remote Development with jRebel
Enable jRebel remote server support in your IDE.
Configure the remote server URL (the ECS service URL).
Deploy the application with the jRebel agent enabled on ECS.
Make changes locally and push them to the remote ECS service, observing the changes take effect immediately.

##Conclusion
This project demonstrates how to set up a simple Spring Boot application with jRebel for efficient local and remote development. 
By using Docker and AWS ECS, you can easily deploy and manage your application in a scalable environment while taking advantage of jRebel's hot-reloading capabilities.
