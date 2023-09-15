# spring-boot-based-microservices

Basic skeleton for Spring Boot Microservices. It includes spring spring security for basic Auth. Spring cloud gateway is also implemented as an Edge Service. Lots of the spring cloud component integrated.

To run the application locally through Docker please follow the below steps:

1. Download and install Docker for Desktop on your pc
2. In the terminal go to the folder of the repo
3. Do `cd docker`where docker-compose.yaml file is placed
4. Execute `docker-compose up`

Jenkinsfile is placed in the repo under root folder to be used in Jenkins pipelines, for CI/CD containing the steps:

- build application
- run tests
- dockerize application
- push docker image in registry
- deploy in kubernetes

A kubernetes-deployment YAML file used to apply in kubernetes clusters in order to deploy the application is put under root folder in repo.
