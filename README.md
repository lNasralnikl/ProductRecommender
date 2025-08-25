# Product Recommender

Рекомендательная система для банка «Стар», которая анализирует транзакции клиентов и предлагает персонализированные банковские продукты.  
Поддерживаются статические и динамические правила, REST API, телеграм-бот, сбор статистики и кэширование.

---

## Стек технологий
- Java 17, Spring Boot
- Gradle
- H2 (read-only) + PostgreSQL (динамические правила)
- Liquibase (миграции во второй БД)
- JDBC + JdbcTemplate
- Caffeine Cache
- Telegram Bot API
- Swagger / OpenAPI

---

## Возможности
- GET /recommendation/{user_id} — рекомендации пользователю.
- CRUD для динамических правил: POST /rule, GET /rule, DELETE /rule/{product_id}.
- Статистика срабатываний: GET /rule/stats.
- Технологический эндпоинт сброса кэшей: POST /management/clear-caches.
- Служебная информация: GET /management/info.
- Телеграм-бот: команда /recommend {username}.

---

## Быстрый старт (Gradle)

### Сборка
bash
./gradlew clean build


### Запуск
bash
# java -jar build/libs/product-recommender.jar

> Подсказка: переменные окружения формируются из свойств Spring Boot: 
application.name → APPLICATION_NAME, spring.datasource.url → SPRING_DATASOURCE_URL и т.п.

---

## Документация
- Вики: ./wiki/Home.md
- OpenAPI спецификация: ./docs/openapi.yaml
- Диаграммы: ./docs/architecture.png, ./docs/activity-diagram.png
- Инструкция по развёртыванию: ./wiki/Развертывание.md
