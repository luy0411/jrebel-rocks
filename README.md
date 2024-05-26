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
git clone https://github.com/luy0411/jrebel-rocks.git
cd jrebel-rocks
```

## Build the Application

Use Maven to build the application:

```
mvn clean install
```

## Run Locally with Docker
Build the Docker image:

```
docker build -t jrebel-rocks .
```

Run the Docker container:

```
docker run -p 8080:8080 jrebel-rocks
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
aws ecr create-repository --repository-name jrebel-rocks
```

## Create Docker Image
Authenticate Docker to your ECR:

```
$(aws ecr get-login --no-include-email --region [your-region]
```

Tag the Docker image:

```
docker tag jrebel-rocks:latest [your-account-id].dkr.ecr.[your-region].amazonaws.com/jrebel-rocks:latest
```

Push Docker Image to ECR

```
docker push [your-account]-id.dkr.ecr.[your-region].amazonaws.com/jrebel-rocks:latest
```

## Create ECS Cluster and Service
Create an ECS cluster:

```
aws ecs create-cluster --cluster-name jrebel-rocks
```

Register a task definition:

Create a file named jrebel-rocks-aws-ecs-td.json (the one below is just an example)
```
{
    "containerDefinitions": [
        {
            "name": "jrebel-rocks",
            "image": "117278927470.dkr.ecr.us-west-2.amazonaws.com/jrebel-rocks:latest",			
			"portMappings": [
				{
				  "name": "8080",
				  "containerPort": 8080,
				  "hostPort": 8080,
				  "protocol": "tcp",
				  "appProtocol": "http"
				}
			],
			"logConfiguration": { 
				"logDriver": "awslogs",
				"options": { 
				   "awslogs-group" : "/ecs/jrebel-rocks",
				   "awslogs-region": "us-west-2",
				   "awslogs-stream-prefix": "ecs"
				}
			},
            "essential": true
        }
    ],
    "family": "jrebel-rocks",	
	"requiresCompatibilities": [ 
       "FARGATE" 
    ],
	"networkMode": "awsvpc",
	"memory": "512",
	"cpu": "256",
	"executionRoleArn": "arn:aws:iam::117278927470:role/ecsTaskExecutionRole"
}
```

Update the jrebel-rocks-aws-ecs-td.json file with your ECR image details and then register it:

```
aws ecs register-task-definition --cli-input-json file://jrebel-rocks-aws-ecs-td.json
```

Create a service:

```
aws ecs create-service --cluster jrebel-rocks --service-name jrebel-rocks --task-definition jrebel-rocks:4 --desired-count 1 --launch-type FARGATE --platform-version LATEST     --network-configuration "awsvpcConfiguration={subnets=[subnet-97f49ef2],securityGroups=[sg-00d1a3c4873bb08d2],assignPublicIp=ENABLED}"
```

## Using jRebel with local server (Docker / Spring)

- Install jRebel plugin in your IDE (IntelliJ IDEA, Eclipse, etc.).

- Run the Spring Boot application using docker
  
- Configure jRebel in your IDE and point the remote server to your docker service (probably localhost:8080)

- Make changes to your code and see them reflect immediately without restarting the application.

## Remote Development with jRebel

 - Enable jRebel remote server support in your IDE.
   
 - Configure the remote server URL (the ECS service URL).
   
 - Deploy the application with the jRebel agent enabled on ECS.
   
 - Make changes locally, save them and observe the changes take effect immediately.

## Conclusion

This project demonstrates how to set up a simple Spring Boot application with jRebel for efficient local and remote development. 

By using Docker and AWS ECS, you can easily deploy and manage your application in a scalable environment while taking advantage of jRebel's hot-reloading capabilities.
