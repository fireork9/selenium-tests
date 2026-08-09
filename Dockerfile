# Переключаемся на базовый образ на базе Debian Bookworm
FROM maven:3.9.4-eclipse-temurin-17-debian-bookworm

# Устанавливаем Chromium и ChromeDriver напрямую из стабильного репозитория
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    unzip \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

# Создаем рабочую папку проекта
WORKDIR /app

# Копируем pom.xml
COPY pom.xml .

# Скачиваем библиотеки в кэш
RUN mvn dependency:go-offline -B

# Копируем код тестов
COPY src ./src

# Команда для автоматического запуска
CMD ["mvn", "clean", "test"]