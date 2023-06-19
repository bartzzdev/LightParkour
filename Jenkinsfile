pipeline {
    agent any

    tools {
        maven 'MAVEN'
        jdk 'JDK'
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