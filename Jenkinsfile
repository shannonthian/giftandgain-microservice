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
         stage('Mvn build docker files') { 
            steps {
                dir('listing'){
                  sh 'docker build -t listing:v3 .' 
               }
            }
        }
    }
}