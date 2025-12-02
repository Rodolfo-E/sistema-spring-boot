pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker-compose -f docker-compose.app.yml down || true
                docker-compose -f docker-compose.app.yml up -d --build
                '''
            }
        }
    }
}