# Используем стабильный образ Maven на базе Debian, где Chromium ставится без проблем
FROM maven:3.9.4-eclipse-temurin-17

# Устанавливаем Chromium и ChromeDriver для Linux
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

# Создаем рабочую папку в контейнере
WORKDIR /app

# Копируем настройки проекта
COPY pom.xml .

# Скачиваем Java-библиотеки в кэш
RUN mvn dependency:go-offline -B

# Копируем исходный код тестов
COPY src ./src

# Команда для запуска UI-тестов
CMD ["mvn", "clean", "test"]