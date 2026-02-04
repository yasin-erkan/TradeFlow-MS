# TradeFlow-MS

> Security-first microservices platform for trade management

## 🚀 Quick Start

```bash
# 1. Start infrastructure
docker-compose up -d

# 2. Start services in order
cd services/config-server && ./mvnw spring-boot:run
cd services/discovery-server && ./mvnw spring-boot:run
cd services/customer-service && ./mvnw spring-boot:run
```

## 🛠️ Tech Stack

- **Backend:** Java 17, Spring Boot 3.4.2, Spring Cloud 2024.0.0
- **Database:** MongoDB (Customers), PostgreSQL (Orders)
- **Service Discovery:** Netflix Eureka
- **Configuration:** Spring Cloud Config

## 📡 Service Ports

| Service          | Port  |
| ---------------- | ----- |
| Config Server    | 8888  |
| Discovery Server | 8761  |
| Customer Service | 8090  |
| MongoDB          | 50000 |

## 📝 Roadmap

- [x] Infrastructure setup (Config Server, Eureka, Docker)
- [x] Customer Service CRUD with MongoDB
- [ ] API Gateway with load balancing
- [ ] Product & Order Services
- [ ] Authentication with Keycloak
