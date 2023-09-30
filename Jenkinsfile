pipeline {
    agent any
    stages {
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
                  sh 'docker tag listing:v3 150615723430.dkr.ecr.us-east-1.amazonaws.com/listing-repository:listingv3' 
               }
            }
        }
         stage('Pushing image to AWS ECR') { 
            steps {
                dir('listing'){
                 script {
                    def ecrLoginCommand = """\$(aws ecr get-login-password)"""
                    def ecrRegistry = "150615723430.dkr.ecr.us-east-1.amazonaws.com"

                    def loginCommand = "docker login -u AWS --password-stdin ${ecrRegistry}"
                    def loginStatus = sh(script: loginCommand, returnStatus: true, input: ecrLoginCommand)

                    if (loginStatus != 0) {
                        error("Docker login failed")
                    }
                }
                  sh 'docker push 150615723430.dkr.ecr.us-east-1.amazonaws.com/listing-repository:listingv3' 
               }
            }
        }
    }
}