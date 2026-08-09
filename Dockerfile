# Используем стабильный образ Java 17 на базе Debian
FROM eclipse-temurin:17-jdk

# Устанавливаем Maven, Chromium и ChromeDriver одной командой
RUN apt-get update && apt-get install -y --no-install-recommends \
    maven \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

# Создаем рабочую папку
WORKDIR /app

# Копируем настройки проекта
COPY pom.xml .

# Скачиваем Java-библиотеки в кэш
RUN mvn dependency:go-offline -B

# Копируем исходный код автотестов
COPY src ./src

# Команда для запуска ваших UI-тестов
CMD ["mvn", "clean", "test"]