pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo '✅ Код склонирован'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('my-selenium-tests')
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    docker.image('my-selenium-tests').inside {
                        sh 'mvn clean test'
                    }
                }
            }
        }

        stage('Collect Results') {
            steps {
                echo '✅ Тесты выполнены'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo '✅ Все тесты прошли успешно!'
        }
        failure {
            echo '❌ Есть упавшие тесты!'
        }
    }
}