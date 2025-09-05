# Users-Budgets Service

Микросервис для управления пользователями и их бюджетами в рамках системы Smart Expenses.

## 📋 Содержание

- [Назначение сервиса](#назначение-сервиса)
- [Технологический стек](#технологический-стек)
- [Архитектура и структура](#архитектура-и-структура)
- [API документация](#api-документация)
- [Руководство по тестированию](#руководство-по-тестированию)
- [Запуск приложения](#запуск-приложения)
- [Конфигурация](#конфигурация)

## 🎯 Назначение сервиса

`users-budgets-service` отвечает за:

- **Управление пользователями**: создание, получение информации о пользователях
- **Управление бюджетами**: создание и получение бюджетов, привязанных к пользователям
- **Безопасность**: JWT аутентификация с проверкой прав доступа
- **Интеграция**: предоставление REST API для взаимодействия с другими микросервисами

## 🛠 Технологический стек

- **Java 23** - основной язык программирования
- **Spring Boot 3.5.x** - фреймворк для создания микросервисов
- **Spring Web** - REST API
- **Spring Data JPA** - работа с базой данных
- **Spring Security** - JWT аутентификация
- **Hibernate ORM** - ORM для работы с БД
- **PostgreSQL** - основная база данных
- **Maven** - управление зависимостями
- **Lombok** - минимизация boilerplate-кода

## 🏗 Архитектура и структура

### Структура проекта

```
src/main/java/com/smart/expense/usersbudgets/
├── config/           # Конфигурация безопасности
├── controller/       # REST контроллеры
├── dto/             # Data Transfer Objects
├── entity/          # JPA сущности
├── exception/       # Обработка исключений
├── repository/      # Репозитории для работы с БД
└── service/         # Бизнес-логика
```

### Описание классов

#### 🗃 Entity (Сущности)

**User.java**
```java
@Entity
@Table(name = "users")
public class User {
    private UUID id;           // Уникальный идентификатор
    private String email;      // Email (уникальный)
    private String name;       // Имя пользователя
    private String password;   // Зашифрованный пароль
    private List<Budget> budgets; // Список бюджетов пользователя
}
```

**Budget.java**
```java
@Entity
@Table(name = "budgets")
public class Budget {
    private UUID id;           // Уникальный идентификатор
    private User user;         // Связь с пользователем
    private String category;   // Категория бюджета
    private BigDecimal amount; // Сумма бюджета
    private String month;      // Месяц в формате YYYY-MM
}
```

#### 🎮 Controller (Контроллеры)

**UserController.java**
- `POST /users` - создание пользователя
- `GET /users/{id}` - получение пользователя по ID
- `GET /users` - получение всех пользователей

**PublicBudgetController.java**
- `POST /budgets/{userId}` - создание бюджета для пользователя
- `GET /budgets/{userId}?month=YYYY-MM` - получение бюджетов пользователя за месяц

**InternalBudgetController.java**
- `GET /internal/budgets-by-user/{userId}?month=YYYY-MM` - внутренний API для других сервисов

#### 🔧 Service (Сервисы)

**UserService.java**
- Создание и управление пользователями
- Шифрование паролей
- Поиск пользователей по email/ID

**BudgetService.java**
- Создание и управление бюджетами
- Поиск бюджетов по пользователю и месяцу
- Валидация данных

#### 🗄 Repository (Репозитории)

**UserRepository.java**
- `findById(UUID id)` - поиск по ID
- `findByEmail(String email)` - поиск по email

**BudgetRepository.java**
- `findByUserAndMonth(User user, String month)` - поиск бюджетов по пользователю и месяцу

#### 📦 DTO (Data Transfer Objects)

**UserDTO.java**
```json
{
  "id": "uuid",
  "email": "user@example.com",
  "name": "User Name",
  "password": "password"
}
```

**BudgetDTO.java**
```json
{
  "id": "uuid",
  "userId": "uuid",
  "category": "Food",
  "amount": 300.50,
  "month": "2025-09"
}
```

## 🔌 API документация

### Users API

#### Создание пользователя
```http
POST /users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Ответ:**
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "email": "john@example.com",
  "name": "John Doe"
}
```

#### Получение пользователя
```http
GET /users/{userId}
Authorization: Bearer {jwt_token}
```

### Budgets API

#### Создание бюджета
```http
POST /budgets/{userId}
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
  "category": "Food",
  "amount": 300.50,
  "month": "2025-09"
}
```

#### Получение бюджетов за месяц
```http
GET /budgets/{userId}?month=2025-09
Authorization: Bearer {jwt_token}
```

**Ответ:**
```json
{
  "budgets": [
    {
      "id": "123e4567-e89b-12d3-a456-426614174001",
      "userId": "123e4567-e89b-12d3-a456-426614174000",
      "category": "Food",
      "amount": 300.50,
      "month": "2025-09"
    }
  ]
}
```

## 🧪 Руководство по тестированию

### Подготовка к тестированию

1. **Запустите приложение:**
   ```bash
   mvn spring-boot:run
   ```

2. **Импортируйте Postman коллекцию:**
   - Откройте Postman
   - Import → File → выберите `usersbudgets.postman_collection.json`

3. **Настройте переменные окружения:**
   - В Postman перейдите в Environments
   - Создайте новое окружение "Users-Budgets"
   - Установите переменные:
     - `baseUrl`: `http://localhost:8082`
     - `jwtToken`: `YOUR_JWT_TOKEN_HERE` (пока оставьте как есть)
     - `userId`: `YOUR_USER_ID_HERE` (будет заполнено автоматически)
     - `budgetId`: `YOUR_BUDGET_ID_HERE` (будет заполнено автоматически)

### Пошаговое тестирование

#### Шаг 1: Создание пользователя

1. Выберите запрос **"1. Create User"** из папки **Test Scenarios**
2. Нажмите **Send**
3. **Ожидаемый результат:** HTTP 201 Created
4. **Скопируйте `id` из ответа** и вставьте в переменную `userId`

**Пример ответа:**
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "email": "test@example.com",
  "name": "Test User"
}
```

#### Шаг 2: Получение JWT токена

⚠️ **Важно:** Для полного тестирования нужен JWT токен от auth-service. 

**Временное решение для тестирования:**
1. Отключите JWT аутентификацию в `SecurityConfig.java`:
   ```java
   .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
   ```
2. Перезапустите приложение
3. Установите `jwtToken` = `"test"` (любое значение)

#### Шаг 3: Тестирование бюджетов

1. **Создание бюджета:**
   - Выберите **"2. Create Multiple Budgets"**
   - Нажмите **Send**
   - **Ожидаемый результат:** HTTP 201 Created

2. **Получение бюджетов:**
   - Выберите **"3. Get All Budgets for Month"**
   - Нажмите **Send**
   - **Ожидаемый результат:** HTTP 200 OK с списком бюджетов

#### Шаг 4: Тестирование безопасности

1. **Попытка доступа к чужим данным:**
   - Выберите **"4. Test Security - Access Another User's Data"**
   - Нажмите **Send**
   - **Ожидаемый результат:** HTTP 403 Forbidden или SecurityException

### Тестирование ошибок

#### Валидация данных

1. **Неверные данные пользователя:**
   - Выберите **"Invalid User Data"** из папки **Error Testing**
   - Нажмите **Send**
   - **Ожидаемый результат:** HTTP 400 Bad Request

2. **Неверные данные бюджета:**
   - Выберите **"Invalid Budget Data"**
   - Нажмите **Send**
   - **Ожидаемый результат:** HTTP 400 Bad Request

3. **Неавторизованный запрос:**
   - Выберите **"Unauthorized Request"**
   - Нажмите **Send**
   - **Ожидаемый результат:** HTTP 401 Unauthorized

### Примеры тестовых данных

#### Создание пользователей
```json
{
  "name": "Alice Johnson",
  "email": "alice@example.com",
  "password": "password123"
}
```

#### Создание бюджетов
```json
{
  "category": "Groceries",
  "amount": 500.00,
  "month": "2025-09"
}
```

```json
{
  "category": "Transport",
  "amount": 150.00,
  "month": "2025-09"
}
```

```json
{
  "category": "Entertainment",
  "amount": 200.00,
  "month": "2025-09"
}
```

### Проверка результатов

После выполнения всех тестов вы должны увидеть:

1. ✅ Созданного пользователя в базе данных
2. ✅ Несколько бюджетов для этого пользователя
3. ✅ Корректную работу валидации
4. ✅ Блокировку неавторизованных запросов
5. ✅ Защиту от доступа к чужим данным

## 🚀 Запуск приложения

### Предварительные требования

- Java 23+
- Maven 3.6+
- PostgreSQL 12+

### Настройка базы данных

1. Создайте базу данных:
   ```sql
   CREATE DATABASE usersbudgets;
   ```

2. Создайте пользователя (опционально):
   ```sql
   CREATE USER postgres WITH PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE usersbudgets TO postgres;
   ```

### Запуск

```bash
# Клонирование репозитория
git clone <repository-url>
cd usersbudgets

# Компиляция
mvn clean compile

# Запуск
mvn spring-boot:run
```

Приложение будет доступно по адресу: `http://localhost:8082`

## ⚙️ Конфигурация

### application.yml

```yaml
server:
  port: 8082

spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${JWT_ISSUER_URI:http://localhost:8080/auth/realms/smart-expenses}
          audience: ${JWT_AUDIENCE:smart-expenses}

  datasource:
    url: jdbc:postgresql://localhost:5432/usersbudgets
    username: postgres
    password: postgres
    
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Переменные окружения

- `JWT_ISSUER_URI` - URL сервиса аутентификации
- `JWT_AUDIENCE` - аудитория JWT токенов
- `SPRING_DATASOURCE_URL` - URL базы данных
- `SPRING_DATASOURCE_USERNAME` - имя пользователя БД
- `SPRING_DATASOURCE_PASSWORD` - пароль БД

## 🔒 Безопасность

- **JWT аутентификация** - все защищенные эндпоинты требуют валидный JWT токен
- **Проверка прав доступа** - пользователи могут работать только со своими данными
- **Шифрование паролей** - пароли хранятся в зашифрованном виде (BCrypt)
- **Валидация данных** - все входящие данные проверяются на корректность

## 📝 Логирование

Приложение использует Logback для логирования. Уровни логирования можно настроить в `application.yml`:

```yaml
logging:
  level:
    com.smart.expense.usersbudgets: DEBUG
    org.springframework.security: DEBUG
    org.hibernate.SQL: DEBUG
```

## 🤝 Интеграция с другими сервисами

Сервис интегрируется с:

- **auth-service** - получение JWT токенов
- **transactions-service** - проверка бюджетов при создании транзакций
- **frontend** - предоставление данных о пользователях и бюджетах

