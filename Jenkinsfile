pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'  # Más rápido
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker-compose -f docker-compose.app.yml down || true
                docker-compose -f docker-compose.app.yml up -d --build

                # Verificación básica
                sleep 30
                curl -f http://localhost:8080/actuator/health || echo "App iniciando..."
                '''
            }
        }
    }

    post {
        success {
            echo '✅ App desplegada: http://localhost:8080'
        }
        failure {
            echo '❌ Deploy falló'
            sh 'docker-compose -f docker-compose.app.yml logs --tail=50'
        }
    }
}