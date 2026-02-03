# TradeFlow-MS Infrastructure Configuration

## Overview

TradeFlow-MS is a microservices-based trading platform using Spring Boot 3.5.0 with a cloud-native architecture.

## Docker Infrastructure (Port Mappings)

All Docker services run on the `tradeflow-net` bridge network with `tf-` prefix:

### Active Services

| Service    | Container Name | Image                     | Host Port | Container Port | Credentials                              |
| ---------- | -------------- | ------------------------- | --------- | -------------- | ---------------------------------------- |
| PostgreSQL | `tf_postgres`  | postgres                  | 5432      | 5432           | `tradeflow_admin` / `tf_strong_password` |
| MongoDB    | `tf_mongodb`   | mongo                     | **50000** | 27017          | `tradeflow_admin` / `tf_strong_password` |
| Kafka      | `tf_kafka`     | confluentinc/cp-kafka     | 9092      | 9092           | -                                        |
| Zookeeper  | `tf_zookeeper` | confluentinc/cp-zookeeper | 2181      | 2181           | -                                        |
| Keycloak   | `tf_keycloak`  | keycloak                  | 8080      | 8080           | `tf_admin` / `tf_admin_pass`             |

### Disabled Services (RAM Friendly Mode)

- `tf-pgadmin` (Port: 5050)
- `tf-mongo-express` (Port: 8081)
- `tf-zipkin` (Port: 9411)
- `tf-mail-dev` (Ports: 1080, 1025)

## Spring Boot Services (Running Locally)

| Service          | Port | Description                          |
| ---------------- | ---- | ------------------------------------ |
| Config Server    | 8888 | Centralized configuration management |
| Discovery Server | 8761 | Service registry (Eureka)            |

## Connection Strings

### PostgreSQL (from local services)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/[database_name]
    username: tradeflow_admin
    password: tf_strong_password
```

### MongoDB (from local services)

```yaml
spring:
  data:
    mongodb:
      host: localhost
      port: 50000 # Important: Mapped from container's 27017
      username: tradeflow_admin
      password: tf_strong_password
      authentication-database: admin
```

### Kafka (from local services)

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
```

### Eureka (from local services)

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Service Startup Order

1. **Docker Infrastructure**

   ```bash
   docker-compose up -d
   ```

2. **Config Server** (Must start first)

   ```bash
   cd services/config-server
   ./mvnw spring-boot:run
   ```

3. **Discovery Server** (Depends on Config Server)

   ```bash
   cd services/discovery-server
   ./mvnw spring-boot:run
   ```

4. **Other Microservices** (Depend on Config + Discovery)
   ```bash
   cd services/[service-name]
   ./mvnw spring-boot:run
   ```

## Configuration Strategy

### Config Server Setup

- Profile: `native`
- Storage: `classpath:/configurations`
- Port: `8888`

### Microservice Configuration

Each microservice has:

- `bootstrap.yml` - Connects to Config Server
- Configuration file in Config Server at `configurations/[service-name].yml`

### Common Configuration

All services inherit from `application-common.yml` which includes:

- Database connections (PostgreSQL + MongoDB)
- Kafka settings
- Eureka client settings
- Actuator endpoints
- Logging configuration

## Standards & Conventions

✅ **Container Naming**: All Docker containers use `tf-` prefix  
✅ **Network**: All services on `tradeflow-net` bridge network  
✅ **Language**: All comments, logs, and documentation in English  
✅ **MongoDB Port**: `50000` for local services (mapped from container `27017`)  
✅ **PostgreSQL Port**: `5432` for local services  
✅ **Restart Policy**: `unless-stopped` for all active Docker services

## Verification Commands

### Check Docker Services

```bash
docker ps
docker-compose ps
```

### Check Spring Boot Services

```bash
# Config Server
curl http://localhost:8888/actuator/health

# Discovery Server
curl http://localhost:8761/actuator/health

# Eureka Dashboard
open http://localhost:8761
```

### Check Infrastructure Services

```bash
# PostgreSQL
psql -h localhost -p 5432 -U tradeflow_admin

# MongoDB
mongosh --host localhost --port 50000 -u tradeflow_admin -p tf_strong_password

# Keycloak
open http://localhost:8080
```

## Troubleshooting

### Port Conflicts

If ports are already in use, check with:

```bash
lsof -i :8888  # Config Server
lsof -i :8761  # Discovery Server
lsof -i :5432  # PostgreSQL
lsof -i :50000 # MongoDB
```

### Config Server Not Loading Configurations

Ensure `spring.profiles.active=native` is set and configurations folder exists.

### Discovery Server Not Starting

1. Ensure Config Server is running first
2. Check `bootstrap.yml` points to correct Config Server URL
3. Verify Config Server has `discovery-server.yml`

### Database Connection Issues

- PostgreSQL: Use port `5432`
- MongoDB: Use port `50000` (not 27017)
- Verify Docker containers are running
- Check credentials match
