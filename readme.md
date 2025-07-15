# 🛡️ LifeQuest Premium Quote Service

A Spring Boot microservice that allows LifeQuest Insurance agents and systems to generate, view, and manage insurance premium quotes for different products (Health, Auto, Life, Annuities).

---

## 🚀 Features

- Generate new quotes based on product, age, state, and coverage
- Automatically estimate premiums using configurable business rules
- List, update, and delete existing quotes
- Supports full (PUT) and partial (PATCH) updates
- Uses in-memory JSON file for storage (`quotes.json`)

---

## 📦 Technologies

- Java 17+
- Spring Boot
- RESTful API
- OpenAPI (Swagger) spec included
- In-memory file-based "DB" (`quotes.json`)

---

## 🏁 Getting Started

### 🔧 Prerequisites

- Java 17+
- Maven 3.8+
- (Optional) Docker for containerization
---

## 📘 API Overview

| Method   | Endpoint           | Description                   |
|----------|--------------------|-------------------------------|
| `POST`   | `/quotes`          | Create a new quote            |
| `GET`    | `/quotes`          | List all quotes               |
| `GET`    | `/quotes/{id}`     | Get quote by ID               |
| `PUT`    | `/quotes/{id}`     | Fully update a quote          |
| `PATCH`  | `/quotes/{id}`     | Partially update quote fields |
| `DELETE` | `/quotes/{id}`     | Delete a quote                |

---

## 📝 Sample Quote JSON

### 🔸 Create Quote (POST)

```json
{
  "productName": "Health Insurance",
  "state": "CA",
  "age": 50,
  "coverageAmount": 100000
}
```

Response:

```json
{
  "id": "QT005",
  "productName": "Health Insurance",
  "state": "CA",
  "age": 50,
  "coverageAmount": 100000,
  "status": "NEW",
  "estimatedPremium": 70.0
}
```

### 🔸 Patch Status (PATCH)

PATCH /quotes/QT003
```json
{
  "status": "IN_REVIEW"
}
```
