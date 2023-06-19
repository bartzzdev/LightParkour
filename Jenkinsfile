pipeline {
    agent any

    stages {
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