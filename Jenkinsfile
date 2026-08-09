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
                    // Используем ваш точный путь к docker.exe в кавычках с двойными слэшами
                    bat '"C:\\Users\\Serega\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" build -t "my-selenium-tests" .'
                }
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    // Запуск тестов через Docker-compose с точным путем
                    bat '"C:\\Users\\Serega\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker-compose.exe" up --build'
                }
            }
        }
    }

    post {
        always {
            echo 'Сборка завершена!'
        }
    }
}