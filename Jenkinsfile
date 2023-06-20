pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {
        stage('Initialize') {
           steps {
                sh 'echo ${JAVA_HOME}'
                sh 'echo ${PATH}'
                sh 'java --version'
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
                echo 'Copying jar to the shared directory..'
                sh 'cp lightparkour-paper/target/LightParkour.jar /artifacts'
            }
        }
    }
}