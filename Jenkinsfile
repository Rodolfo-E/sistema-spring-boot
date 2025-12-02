pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'cd sistema && mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                cd sistema
                docker-compose -f docker-compose.app.yml down || true
                docker-compose -f docker-compose.app.yml up -d --build
                '''
            }
        }
    }
}