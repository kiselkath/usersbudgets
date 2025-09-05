# Users-Budgets Service

A microservice for managing users and their budgets in the Smart Expenses system.

## 📋 Table of Contents

- Purpose
- Tech Stack
- Architecture & Structure
- API Documentation
- Testing Guide
- Running the App
- Configuration

## 🎯 Purpose

`users-budgets-service` provides:

- User management: create and fetch users
- Budgets management: create and fetch user budgets
- Security: authentication strictly via external JWT (Bearer Token). No passwords are stored or processed here
- Integration: REST API for other microservices and frontend

## 🛠 Tech Stack

- Java 23
- Spring Boot 3.5.x
- Spring Web (REST)
- Spring Data JPA (Hibernate)
- Spring Security (OAuth2 Resource Server, JWT)
- PostgreSQL
- Maven
- Lombok

## 🏗 Architecture & Structure

```
src/main/java/com/smart/expense/usersbudgets/
├── config/           # Security (JWT Resource Server)
├── controller/       # REST controllers
├── dto/              # DTOs
├── entity/           # JPA entities
├── exception/        # Exceptions
├── repository/       # Repositories
└── service/          # Business logic
```

### Key Classes

- `entity/User.java`: UUID `id`, `email`, `name`, `@OneToMany` budgets
- `entity/Budget.java`: UUID `id`, `user`, `category`, `amount`, `month`
- `repository/UserRepository.java`: `findById`, `findByEmail`
- `repository/BudgetRepository.java`: `findByUserAndMonth(User user, String month)`
- `service/UserService.java`: CRUD without passwords
- `service/BudgetService.java`: budget operations
- `config/SecurityConfig.java`: JWT resource server, all requests require Bearer token

## 🔐 Security

- Authentication is performed by an external `auth-service` (e.g., Keycloak/Google OAuth2)
- This service accepts Bearer JWT, does not store or process passwords
- Identity check: the `userId` in URL must match the subject from the token (`Authentication#getName()`)

## 🌐 API Documentation

### Users

- `POST /users` — create user
  - Body:
    ```json
    { "name": "John Doe", "email": "john@example.com" }
    ```
  - Response 201:
    ```json
    { "id": "uuid", "email": "john@example.com", "name": "John Doe" }
    ```

- `GET /users/{id}` — fetch user by ID (Bearer JWT required, id must match token)

- `GET /users` — list users (Bearer JWT required)

### Budgets

- `POST /budgets/{userId}` — create a budget for user (Bearer JWT required; `userId` must be yours)
  - Body:
    ```json
    { "category": "Food", "amount": 300.50, "month": "2025-09" }
    ```

- `GET /budgets/{userId}?month=YYYY-MM` — get budgets for a user and month (Bearer JWT required)

### Internal

- `GET /internal/budgets-by-user/{userId}?month=YYYY-MM` — internal endpoint (Bearer JWT required)

## 🧪 Testing Guide (Postman)

1) Import `usersbudgets.postman_collection.json` into Postman.

2) Create an environment with variables:
- `baseUrl` = `http://localhost:8082`
- `jwtToken` = your JWT from external `auth-service`
- `userId` = your UUID (from token or created user)

3) Getting a JWT:
- JWT is issued by external auth (e.g., Keycloak). Put it as `Authorization: Bearer {{jwtToken}}`.

4) Test cases:
- Create user: `POST /users` (no password)
- Create budget: `POST /budgets/{{userId}}`
- Fetch budgets for month: `GET /budgets/{{userId}}?month=2025-09`
- Security check: try another `userId` — expect 403/security error

## 🚀 Running

```bash
mvn clean compile
mvn spring-boot:run
```

App URL: `http://localhost:8082`

## ⚙️ Configuration

`application.yml` uses JWT `issuer-uri` and PostgreSQL connection. User passwords are not used in this service.

## 🐳 Run with Docker Compose

Ensure the external network exists (used by compose):

```bash
docker network create smart-expense-net || true
```

Build and start services in background:

```bash
docker compose up -d --build
```

Tail application logs:

```bash
docker compose logs -f usersbudgets-service
```

Stop without removing data:

```bash
docker compose stop
```

Full stop and remove containers (DB volume preserved):

```bash
docker compose down
```

Rebuild only the app service:

```bash
docker compose up -d --build usersbudgets-service
```

Useful environment overrides (can be added/overridden in compose):

```bash
# Examples
JWT_ISSUER_URI=http://auth:8080/realms/smart-expenses
JWT_AUDIENCE=smart-expenses
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/smart_expense_db
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=password
```

