# TradeFlow Configuration Repository

This directory contains configuration files for all TradeFlow microservices.

## Infrastructure Details

### Docker Services (All running on `tradeflow-net` bridge network)

- **PostgreSQL**: `tf-postgres`
  - Port: `5432`
  - Username: `tradeflow_admin`
  - Password: `tf_strong_password`
  - Connection: `jdbc:postgresql://localhost:5432/[database_name]`

- **MongoDB**: `tf-mongodb`
  - Port: `50000` (mapped from container port 27017)
  - Username: `tradeflow_admin`
  - Password: `tf_strong_password`
  - Connection: `mongodb://localhost:50000`

- **Kafka**: `tf-kafka`
  - Port: `9092`
  - Zookeeper: `tf-zookeeper:2181`
  - Bootstrap Servers: `localhost:9092`

- **Keycloak**: `tf-keycloak`
  - Port: `8080`
  - Admin: `tf_admin` / `tf_admin_pass`

### Spring Boot Services (Running locally via terminal)

- **Config Server**: Port `8888`
- **Discovery Server (Eureka)**: Port `8761`

## Configuration Files

- `application-common.yml` - Shared configuration for all microservices (databases, Kafka, Eureka)
- `discovery-server.yml` - Eureka Server specific configuration
- `[service-name].yml` - Service-specific configuration files

## Usage

Services fetch their configuration from Config Server at startup using `bootstrap.yml`:

```yaml
spring:
  application:
    name: [service-name]
  cloud:
    config:
      uri: http://localhost:8888
```

## Standards

- All comments and logs must be in English
- All Docker containers use `tf-` prefix
- All services connect to `tradeflow-net` network
- MongoDB port is `50000` for local Spring Boot services
- PostgreSQL port is `5432` for local Spring Boot services
