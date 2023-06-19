pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                echo 'Compiling..'
                withMaven {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
                withMaven {
                    sh 'mvn test'
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Building jar..'
                withMaven {
                    sh 'mvn package'
                }
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