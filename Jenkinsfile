pipeline {
    agent any
    tools {
            maven 'apache-maven-3.8.1'
        }

    environment {
        IMAGE_NAME = 'spring-boot-based-microservices-test'
        REGISTRY_URL = 'https://myregistry.com/'
        REGISTRY_CREDENTIALS_ID = 'myPredefinedCredentialsInJenkins'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout your source code repository (e.g., Git)
                checkout scm
            }
        }

        stage('Build and Package') {
            steps {
                // Build and package your Java microservice (e.g., Maven or Gradle)
                echo "Building the application"
                sh 'mvn -DskipTests clean package'
            }
        }

         stage('Test') {
                    steps {
                        // Run your basic tests (e.g., unit tests, integration tests)
                        echo "Running the tests"
                        sh 'mvn test'
                        junit 'target/surefire-reports/*.xml'
                    }
                }

        stage('Dockerize') {
            steps {
                // Build a Docker image for your microservice
                echo "Building the docker image"
                def dockerfile = 'docker-compose.yml'
                //sh 'docker build -t ${IMAGE_NAME} docker/.'
                def dockerImage = docker.build("${IMAGE_NAME}:${env.BUILD_ID}","-f ${dockerfile} ./docker")
            }
        }

        stage('Push to Registry') {
            steps {
                // Push the Docker image to a container registry (e.g., Docker Hub, GCR)
                echo "Pushing docker image to registry"
                //sh 'docker push ${IMAGE_NAME}'
                docker.withRegistry(${REGISTRY_URL}, ${REGISTRY_CREDENTIALS_ID})
                dockerImage.push('latest')
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                // Use kubectl to deploy your application to Kubernetes
                echo "Deploying to Kubernetes"
                sh 'kubectl apply -f kubernetes-deployment.yaml'
            }
        }

    }

    post {
        success {
            // Notify or perform additional tasks on success
            echo 'Deployment and tests passed successfully.'
        }
        failure {
            // Notify or perform additional tasks on failure
            echo 'Deployment or tests failed.'
        }
    }
}
