pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK19'
    }

    stages {
        stage('Initialize') {
           steps {
                echo 'PATH = ${M2_HOME}/bin:${PATH}'
                echo 'M2_HOME = /opt/maven'
           }
        }
        stage('Compile') {
            steps {
                echo 'Compiling..'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
                sh 'mvn test'
            }
        }

        stage('Build') {
            steps {
                echo 'Building jar..'
                sh 'mvn package'
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving jar..'
                archiveArtifacts artifacts: 'lightparkour-paper/target/LightParkour.jar', fingerprint: true
            }
        }
    }
}