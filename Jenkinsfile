pipeline {
    // Запускаем на любом доступном агенте (можно заменить на метку, например 'docker-gradle')
    agent any

    // Определяем переменные, чтобы не дублировать пути
    environment {
        GRADLE_USER_HOME = "${env.WORKSPACE}/.gradle"
    }

    stages {
        stage('Checkout') {
            steps {
                // Jenkins автоматически клонирует репозиторий, если задача настроена на SCM.
                // Но для явности можно написать:
                checkout scm
                // Даём права на выполнение gradlew (для Linux/macOS агентов)
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                echo 'Building with Gradle...'
                // Сборка проекта без запуска тестов (для ускорения, тесты отдельно)
                sh './gradlew clean build -x test'
            }
            post {
                success {
                    // Сохраняем собранный JAR/WAR как артефакт
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh './gradlew test'
            }
            post {
                always {
                    // Публикуем отчёты о тестах в формате JUnit
                    junit testResults: '**/build/test-results/test/*.xml', skipIfNoTestFiles: true
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                // Здесь можно добавить реальный деплой, например:
                // - Запуск через Docker
                // - Копирование JAR на сервер через SSH
                // - Вызов оркестратора (K8s, Nomad)
                // Пример для Docker (если есть Dockerfile):
                // sh 'docker build -t e-commerce-demo:${BUILD_NUMBER} .'
                // sh 'docker push ...'
                // Или просто запуск Spring Boot приложения (для теста):
                // sh 'nohup java -jar build/libs/*.jar &'
                echo 'Deployment step – add your logic here.'
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed! Check the logs and fix the issues.'
            // Можно отправить уведомление в Slack, email и т.д.
        }
        success {
            echo 'Pipeline succeeded! Application built and tested.'
        }
    }
}