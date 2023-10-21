pipeline {
    agent any
    environment {
        ECR_REGISTRY = '150615723430.dkr.ecr.us-east-1.amazonaws.com'
    }
    stages {
          stage('Check Network Access') {
            steps {
                script {
                    def registryHost = 'registry.npmjs.org'
                    def pingCommand = "ping -c 4 ${registryHost}"  // Adjust the ping command as needed

                    // Run the ping command and capture the output
                    def pingResult = sh(script: pingCommand, returnStatus: true, returnStdout: true)

                    if (pingResult == 0) {
                        echo "Network access to ${registryHost} is available."
                    } else {
                        error "Network access to ${registryHost} is not available."
                    }
                }
            }
        }
         stage('AWS ECR - Login') {
            steps {
                script {
                  withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'giftandgain-aws', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                       sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ${ECR_REGISTRY}'
                  }

                }
                 
            }
        }
        stage('Mvn build jar files') { 
            steps {
                dir('inventory-management'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('report'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('userservice'){
                  sh 'mvn clean install -DskipTests=true' 
               }
            }
        }
         stage('Build docker image') { 
            steps {
                dir('inventory-management'){
                  sh 'docker build -t inventory:v3 .'
                  sh 'docker tag inventory:v3 ${ECR_REGISTRY}/listing-repository:inventory' 
               }
                dir('report'){
                  sh 'docker build -t report:v3 .'
                  sh 'docker tag report:v3 ${ECR_REGISTRY}/listing-repository:report' 
               }
                dir('userservice'){
                  sh 'docker build -t userservice:v3 .'
                  sh 'docker tag userservice:v3 ${ECR_REGISTRY}/listing-repository:userservice' 
               }
                dir('frontend'){
                  sh 'docker build -t frontend:v4 .'
                  sh 'docker tag frontend:v4 ${ECR_REGISTRY}/listing-repository:frontend' 
               }
            }
        }
         stage('Pushing image to AWS ECR') { 
            steps {
                dir('inventory-management'){
                       sh 'docker push ${ECR_REGISTRY}/listing-repository:inventory' 
            }
                dir('report'){
                       sh 'docker push ${ECR_REGISTRY}/listing-repository:report' 
            }
                dir('userservice'){
                       sh 'docker push ${ECR_REGISTRY}/listing-repository:userservice' 
            }
                dir('frontend'){
                       sh 'docker push ${ECR_REGISTRY}/listing-repository:frontend' 
            }
        }
      }
         stage ('Remove Image from Jenkins'){
        steps{
            script{
                sh 'docker rmi ${ECR_REGISTRY}/listing-repository:inventory'
                sh 'docker rmi ${ECR_REGISTRY}/listing-repository:report'
                sh 'docker rmi ${ECR_REGISTRY}/listing-repository:userservice'
                sh 'docker rmi ${ECR_REGISTRY}/listing-repository:frontend'
            }
        }
       }
    }
}