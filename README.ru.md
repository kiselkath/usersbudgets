# Users-Budgets Service

Микросервис для управления пользователями и их бюджетами в рамках системы Smart Expenses.

## 📋 Содержание

- Назначение сервиса
- Технологический стек
- Архитектура и структура
- API документация
- Руководство по тестированию
- Запуск приложения
- Конфигурация

## 🎯 Назначение сервиса

`users-budgets-service` отвечает за:

- Управление пользователями: создание, получение информации
- Управление бюджетами: создание и получение бюджетов, привязанных к пользователям
- Безопасность: аутентификация только по внешнему JWT (Bearer Token), пароли в сервисе не хранятся
- Интеграция: REST API для взаимодействия с другими микросервисами

## 🛠 Технологический стек

- Java 23
- Spring Boot 3.5.x
- Spring Web (REST API)
- Spring Data JPA (Hibernate)
- Spring Security (OAuth2 Resource Server, JWT)
- PostgreSQL
- Maven
- Lombok

## 🏗 Архитектура и структура

```
src/main/java/com/smart/expense/usersbudgets/
├── config/           # Безопасность (JWT Resource Server)
├── controller/       # REST контроллеры
├── dto/              # DTO-модели для API
├── entity/           # JPA-сущности
├── exception/        # Исключения
├── repository/       # Репозитории
└── service/          # Бизнес-логика
```

### Ключевые классы

- `entity/User.java`: UUID `id`, `email`, `name`, связь `@OneToMany` с `Budget`
- `entity/Budget.java`: UUID `id`, `user`, `category`, `amount`, `month`
- `repository/UserRepository.java`: `findById`, `findByEmail`
- `repository/BudgetRepository.java`: `findByUserAndMonth(User user, String month)`
- `service/UserService.java`: CRUD без паролей (пароли отсутствуют в принципе)
- `service/BudgetService.java`: операции с бюджетами
- `config/SecurityConfig.java`: ресурс-сервер JWT, все запросы требуют Bearer токен

## 🔐 Безопасность

- Аутентификация осуществляется внешним `auth-service` (например, Keycloak/Google OAuth2)
- Сервис принимает только Bearer JWT токен и не хранит/не обрабатывает пароли
- Проверка идентичности: `userId` в URL должен совпадать с `sub` (или `preferred_username`) из токена (`Authentication#getName()`)

## 🌐 API документация

### Users

- `POST /users` — создать пользователя
  - Тело:
    ```json
    { "name": "John Doe", "email": "john@example.com" }
    ```
  - Ответ 201:
    ```json
    { "id": "uuid", "email": "john@example.com", "name": "John Doe" }
    ```

- `GET /users/{id}` — получить пользователя по ID (требуется Bearer JWT, id должен совпадать с токеном)

- `GET /users` — список пользователей (требуется Bearer JWT)

### Budgets

- `POST /budgets/{userId}` — создать бюджет для пользователя (требуется Bearer JWT; `userId` должен быть вашим)
  - Тело:
    ```json
    { "category": "Food", "amount": 300.50, "month": "2025-09" }
    ```

- `GET /budgets/{userId}?month=YYYY-MM` — получить бюджеты пользователя за месяц (требуется Bearer JWT)

### Internal

- `GET /internal/budgets-by-user/{userId}?month=YYYY-MM` — внутренний эндпоинт (требуется Bearer JWT)

## 🧪 Руководство по тестированию (Postman)

1) Импортируйте коллекцию `usersbudgets.postman_collection.json` в Postman.

2) Создайте окружение с переменными:
- `baseUrl` = `http://localhost:8082`
- `jwtToken` = ваш JWT из внешнего `auth-service`
- `userId` = ваш UUID (из токена или из созданного пользователя)

3) Получение JWT:
- JWT выдается внешним сервисом (например, Keycloak). Вставьте его как `Bearer {{jwtToken}}` в заголовок `Authorization`.

4) Тест-кейсы:
- Создать пользователя: `POST /users` (без пароля)
- Создать бюджет: `POST /budgets/{{userId}}`
- Получить бюджеты за месяц: `GET /budgets/{{userId}}?month=2025-09`
- Проверка безопасности: попробуйте запросить данные другого `userId` — ожидать 403/ошибку безопасности

## 🚀 Запуск

```bash
mvn clean compile
mvn spring-boot:run
```

Приложение: `http://localhost:8082`

## ⚙️ Конфигурация

`application.yml` использует настройки JWT `issuer-uri` и подключения к PostgreSQL. Пароли пользователей в сервисе не используются.

