# Используем образ с Java и Maven
FROM maven:3.9.4-eclipse-temurin-17

# Устанавливаем Chrome для headless-режима
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

# Создаём рабочую папку
WORKDIR /app

# Копируем проект в контейнер
COPY pom.xml .
COPY src ./src

# Скачиваем зависимости
RUN mvn dependency:go-offline

# Команда для запуска тестов
CMD ["mvn", "clean", "test"]