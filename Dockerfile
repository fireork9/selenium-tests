FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y --no-install-recommends \
    maven \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Передаем переменную окружения прямо в контейнер!
ENV CHROME_HEADLESS=true

COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src

CMD ["mvn", "clean", "test"]