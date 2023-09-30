pipeline {
    agent any
    environment {
        ECR_REGISTRY = '150615723430.dkr.ecr.us-east-1.amazonaws.com'
    }
    stages {
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
                dir('listing'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('report'){
                  sh 'mvn clean install -DskipTests=true' 
               }
            }
        }
         stage('Build docker image') { 
            steps {
                dir('listing'){
                  sh 'docker build -t listing:v3 .'
                  sh 'docker tag listing:v3 ${ECR_REGISTRY}/listing-repository:listingv3' 
               }
                dir('inventory-management'){
                  sh 'docker build -t inventory:v3 .'
                  sh 'docker tag inventory:v3 ${ECR_REGISTRY}/listing-repository:inventory' 
               }
                dir('report'){
                  sh 'docker build -t report:v3 .'
                  sh 'docker tag report:v3 ${ECR_REGISTRY}/listing-repository:report' 
               }
            }
        }
         stage('Pushing image to AWS ECR') { 
            steps {
                dir('listing'){
                       sh 'docker push ${ECR_REGISTRY}/listing-repository:listingv3' 
            }
                dir('inventory-management'){
                       sh 'docker push ${ECR_REGISTRY}/listing-repository:inventory' 
            }
                dir('report'){
                       sh 'docker push ${ECR_REGISTRY}/listing-repository:report' 
            }
        }
      }
         stage ('Remove Image from Jenkins'){
        steps{
            script{
                sh 'docker rmi ${ECR_REGISTRY}/listing-repository:listingv3'
                sh 'docker rmi ${ECR_REGISTRY}/listing-repository:inventory'
                sh 'docker rmi ${ECR_REGISTRY}/listing-repository:report'
            }
        }
       }
    }
}