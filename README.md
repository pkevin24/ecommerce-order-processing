# E-Commerce Order Processing System

A microservices-based e-commerce backend for order creation and inventory validation, built with Java 17, Spring Boot 3.5.3, and Apache Kafka.

## Architecture

The system uses an **event-driven choreography** pattern — services communicate asynchronously via Kafka rather than direct HTTP calls.

```
Client
  │
  ▼
Order Service (POST /api/orders)
  │  publishes OrderCreatedEvent
  ▼
Kafka: order-created
  │
  ▼
OrderOrchestrator (within Order Service)
  │  publishes InventoryCheckEvent
  ▼
Kafka: inventory-check
  │
  ▼
Inventory Service
  │  publishes InventoryResponseEvent
  ▼
Kafka: inventory-response
  │
  ▼
OrderOrchestrator → updates order status
```

## Services

| Service | Port | Database | Responsibility |
|---|---|---|---|
| `order-service` | 8080 | `order` (PostgreSQL :5432) | Accepts orders, orchestrates workflow |
| `inventory-service` | 8081 | `inventory` (PostgreSQL :5433) | Validates stock availability |
| `shared-common` | — | — | Shared Kafka events and configuration |

## Kafka Topics

| Topic | Producer | Consumer |
|---|---|---|
| `order-created` | Order Service | OrderOrchestrator |
| `inventory-check` | OrderOrchestrator | Inventory Service |
| `inventory-response` | Inventory Service | OrderOrchestrator |

## Tech Stack

- **Java 17**, **Spring Boot 3.5.3**, **Spring Cloud 2025.0.0**
- **Apache Kafka 3.6.0** with Spring Kafka
- **PostgreSQL** (production), **H2** (tests)
- **Spring Data JPA** / Hibernate
- **Micrometer + Prometheus** for metrics
- **TestContainers** for integration tests
- **GitHub Actions** CI/CD with Trivy security scanning

## Prerequisites

- Java 17+
- Maven 3.x
- Docker (for PostgreSQL and Kafka)

## Getting Started

### 1. Start infrastructure

```bash
# PostgreSQL for order-service
docker run -d --name order-db -e POSTGRES_DB=order -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres

# PostgreSQL for inventory-service
docker run -d --name inventory-db -e POSTGRES_DB=inventory -e POSTGRES_PASSWORD=postgres -p 5433:5432 postgres

# Kafka
docker run -d --name kafka -p 9092:9092 apache/kafka:3.6.0
```

### 2. Initialize the database

```bash
psql -h localhost -U postgres -d inventory -f sql_script.sql
```

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the services

```bash
# Order Service
cd order-service && mvn spring-boot:run

# Inventory Service
cd inventory-service && mvn spring-boot:run
```

## API

### Create an Order

```
POST http://localhost:8080/api/orders
Content-Type: application/json
```

```json
{
  "customerId": "customer-123",
  "items": [
    { "productId": "prod-001", "quantity": 2 }
  ],
  "shippingAddress": "123 Main St"
}
```

**Response:** `200 OK` — `Order request placed successfully`

## Running Tests

```bash
# Unit tests
mvn test

# Integration tests (requires Docker)
mvn verify -Pintegration-test
```

## Project Structure

```
ecommerce-order-processing/
├── order-service/        # Order management microservice
├── inventory-service/    # Inventory management microservice
├── shared-common/        # Shared events and Kafka config
├── sql_script.sql        # Inventory seed data (8 products)
└── pom.xml               # Multi-module Maven root
```

## CI/CD

GitHub Actions runs on push to `master` or `develop`:

1. **Test** — unit and integration tests
2. **Build** — packages JARs
3. **Security Scan** — Trivy vulnerability scan

Optional Maven profiles: `-Pcode-quality` (SonarQube, SpotBugs, Checkstyle, JaCoCo).

## Planned Services

- Payment Service *(stub in pom.xml)*
- Notification Service *(stub in pom.xml)*
