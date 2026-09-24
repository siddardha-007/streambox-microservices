# StreamBox

**StreamBox** is a backend-focused movie discovery and personal tracking platform built using **Java, Spring Boot, and Microservices Architecture**.

The project integrates the **TMDB API** to provide movie information and uses independent microservices for authentication, watchlists, ratings, and watch history.

The main goal of StreamBox is to implement and understand real-world microservices concepts such as service-to-service communication, distributed authentication, service discovery, API gateways, event-driven architecture, caching, fault tolerance, and observability.

---

## Architecture

```text
                         Client
                           |
                           v
                    +--------------+
                    | API Gateway  |
                    +------+-------+
                           |
        +------------------+------------------+
        |                  |                  |
        v                  v                  v
 +-------------+    +-------------+    +-------------+
 | Auth        |    | Movie       |    | Watchlist   |
 | Service     |    | Service     |    | Service     |
 +------+------+    +------+------+    +------+------+
        |                  |                  |
        v                  v                  v
   PostgreSQL             TMDB            PostgreSQL


        +------------------+------------------+
        |                  |                  |
        v                  v                  v
 +-------------+    +-------------+    +-------------+
 | Rating      |    | History     |    | Recommendation|
 | Service     |    | Service     |    | Service      |
 +------+------+    +------+------+    +-------------+
        |                  |
        v                  v
   PostgreSQL          PostgreSQL


                    +-------------+
                    |    Kafka    |
                    +------+------+
                           |
              +------------+------------+
              |                         |
              v                         v
      Notification Service     Recommendation Service
```

> The architecture diagram represents the planned final architecture. Some infrastructure components are currently under development.

---

## Core Features

* JWT-based authentication and authorization
* User registration and login
* Movie discovery using TMDB
* Popular, trending, and top-rated movies
* Movie search
* Movie details and similar movies
* Personal watchlist management
* Movie ratings and reviews
* Personal watch history
* Service-to-service communication using OpenFeign
* Database-per-service architecture
* Stateless authentication
* RESTful APIs

---

## Microservices

| Service                | Responsibility                                  | Status    |
| ---------------------- | ----------------------------------------------- | --------- |
| Auth Service           | User registration, login and JWT authentication | Completed |
| Movie Service          | Movie information and TMDB integration          | Completed |
| Watchlist Service      | Manage user's watchlist                         | Completed |
| Rating Service         | Manage ratings and reviews                      | Completed |
| History Service        | Track user's watched movies                     | Completed |
| Notification Service   | Event-based user notifications                  | Planned   |
| Recommendation Service | Personalized movie recommendations              | Planned   |
| API Gateway            | Central entry point for services                | Planned   |
| Eureka Server          | Service discovery                               | Planned   |
| Config Server          | Centralized configuration                       | Planned   |

---

## Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Cloud
* OpenFeign
* REST APIs
* Maven

### Database

* PostgreSQL

### External Services

* TMDB API

### Planned Infrastructure

* Spring Cloud Gateway
* Netflix Eureka
* Apache Kafka
* Redis
* Resilience4j
* Spring Cloud Config
* OpenTelemetry
* Jaeger
* Docker
* Docker Compose

---

## Service Communication

### Synchronous Communication

StreamBox currently uses **OpenFeign** for communication between microservices.

For example:

```text
Watchlist Service
       |
       | OpenFeign
       v
 Movie Service
       |
       v
    TMDB API
```

Before adding a movie to a user's watchlist, the Watchlist Service communicates with the Movie Service to verify that the movie exists.

The same approach is used by the Rating and History services.

### Asynchronous Communication

Apache Kafka will be introduced for event-driven communication.

Planned flow:

```text
Rating Service
      |
      v
    Kafka
      |
      +------> Notification Service
      |
      +------> Recommendation Service
```

---

## Database Architecture

StreamBox follows the **Database-per-Service** principle.

Each business service owns its own database.

```text
Auth Service
     |
     +----> streambox_auth_db

Watchlist Service
     |
     +----> streambox_watchlist_db

Rating Service
     |
     +----> streambox_rating_db

History Service
     |
     +----> streambox_history_db

Movie Service
     |
     +----> TMDB API
```

Services do not directly access another service's database.

---

## Authentication

StreamBox uses JWT-based stateless authentication.

```text
Client
  |
  v
Auth Service
  |
  +---- Register
  |
  +---- Login
          |
          v
      JWT Token
          |
          v
    Other Services
          |
          v
    JWT Validation
```

The authenticated user's ID is extracted from the JWT instead of accepting the `userId` directly from the client request.

---

# Running the Application Locally

## Prerequisites

Make sure the following are installed:

* Java 21
* Maven
* PostgreSQL
* Git
* TMDB API account/token
* IntelliJ IDEA or another Java IDE

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

Verify PostgreSQL is running before starting the services.

---

## 1. Clone the Repository

```bash
git clone https://github.com/siddardha-007/streambox-microservices.git
```

Navigate into the project:

```bash
cd streambox-microservices
```

---

## 2. Create PostgreSQL Databases

Open PostgreSQL using pgAdmin or `psql` and create the following databases:

```sql
CREATE DATABASE streambox_auth_db;
CREATE DATABASE streambox_watchlist_db;
CREATE DATABASE streambox_rating_db;
CREATE DATABASE streambox_history_db;
```

The Movie Service currently does not require a PostgreSQL database because movie information is retrieved from TMDB.

---

## 3. Configure Database Credentials

Each service contains its own configuration.

For example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/streambox_auth_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD
```

Update the PostgreSQL username and password according to your local setup.

Repeat the configuration for:

```text
auth-service
watchlist-service
rating-service
history-service
```

---

## 4. Configure TMDB API

The Movie Service requires a TMDB access token.

Set the environment variable:

### Windows PowerShell

```powershell
$env:TMDB_ACCESS_TOKEN="your_tmdb_access_token"
```

### Windows Command Prompt

```cmd
set TMDB_ACCESS_TOKEN=your_tmdb_access_token
```

The Movie Service uses:

```properties
tmdb.base-url=https://api.themoviedb.org/3
tmdb.access-token=${TMDB_ACCESS_TOKEN}
```

Do not commit your TMDB token or database passwords to GitHub.

---

## 5. Configure JWT Secret

The Auth, Watchlist, Rating, and History services use the same JWT secret.

Set:

### Windows PowerShell

```powershell
$env:JWT_SECRET="your_long_secure_secret"
```

Or configure the value through your IDE's environment variables.

---

## 6. Start the Services

Start each Spring Boot application separately.

### Auth Service

```text
Port: 8081
```

### Movie Service

```text
Port: 8082
```

### Watchlist Service

```text
Port: 8083
```

### Rating Service

```text
Port: 8084
```

### History Service

```text
Port: 8085
```

You can run them directly from IntelliJ IDEA or using Maven:

```bash
mvn spring-boot:run
```

Run the command inside each service directory.

---

## 7. Test the APIs

The recommended testing order is:

```text
1. Register User
       |
       v
2. Login
       |
       v
3. Copy JWT Token
       |
       v
4. Use Token for protected APIs
       |
       +---- Watchlist
       |
       +---- Ratings
       |
       +---- History
```

Example:

```http
POST http://localhost:8081/auth/register
```

```http
POST http://localhost:8081/auth/login
```

Then use the returned token:

```http
Authorization: Bearer <JWT_TOKEN>
```

Movie APIs can be tested directly through:

```text
http://localhost:8082/movies/popular
http://localhost:8082/movies/trending
http://localhost:8082/movies/top-rated
```

---

## Project Structure

```text
streambox-microservices/
│
├── auth-service/
│   └── src/
│
├── movie-service/
│   └── src/
│
├── watchlist-service/
│   └── src/
│
├── rating-service/
│   └── src/
│
├── history-service/
│   └── src/
│
├── notification-service/        # Planned
├── recommendation-service/      # Planned
├── api-gateway/                 # Planned
├── eureka-server/               # Planned
└── config-server/               # Planned
```

---

# Current Implementation

The following components are currently implemented and working:

* [x] Microservices project structure
* [x] Auth Service
* [x] User registration
* [x] User login
* [x] JWT authentication
* [x] PostgreSQL integration
* [x] Movie Service
* [x] TMDB API integration
* [x] Movie search
* [x] Movie details
* [x] Watchlist Service
* [x] Rating Service
* [x] History Service
* [x] JWT security across services
* [x] OpenFeign communication
* [x] Movie validation through Movie Service
* [x] All current REST APIs tested successfully
* [x] Security and integration testing
* [x] Common exception handling
* [x] Eureka Service Discovery
* [x] API Gateway
* [x] Service-to-service communication through service discovery
* [x] Netflix Eureka
* [x] Spring Cloud Gateway

---

# Currently Working On

The next development stage focuses on improving the microservices infrastructure.

* [ ] Apache Kafka
* [ ] Event-driven architecture
* [ ] Notification Service
---

# Future Implementation

The following features are planned for future development:

### Infrastructure


* [ ] Spring Cloud Config Server
* [ ] Docker and Docker Compose

### Communication

* [ ] Apache Kafka
* [ ] Event-driven architecture
* [ ] Notification Service

### Performance and Reliability

* [ ] Redis caching
* [ ] Resilience4j
* [ ] Circuit Breaker
* [ ] Retry mechanism
* [ ] Timeout and fallback handling

### Observability

* [ ] Spring Boot Actuator
* [ ] OpenTelemetry
* [ ] Jaeger distributed tracing
* [ ] Centralized logging

### Application Features

* [ ] Recommendation Service
* [ ] Personalized movie recommendations
* [ ] React frontend
* [ ] Swagger / OpenAPI documentation
* [ ] Automated integration testing
* [ ] CI/CD pipeline

---

# Development Roadmap

```text
Core Microservices
       |
       v
JWT Security
       |
       v
OpenFeign
       |
       v
Eureka Service Discovery
       |
       v
API Gateway
       |
       v
Kafka
       |
       v
Redis + Resilience4j
       |
       v
Config Server
       |
       v
Observability
       |
       v
Docker + CI/CD
       |
       v
Complete StreamBox Platform
```

---

# Learning Objectives

StreamBox is being developed to gain practical experience with production-style backend architecture and distributed systems.

Key concepts covered:

* Microservices Architecture
* Database-per-Service
* JWT Authentication
* Service-to-Service Communication
* Service Discovery
* API Gateway
* Event-Driven Architecture
* Message Brokers
* Caching
* Fault Tolerance
* Distributed Tracing
* Centralized Configuration
* Containerization
* CI/CD

---

# Project Status

**Status: In Active Development**

The core business services are implemented and communicating successfully. Infrastructure components such as service discovery, API Gateway, Kafka, Redis, resilience, observability, and containerization are being added incrementally.

---

# Author

**Siddardha Bangaru**

B.Tech — Computer Science and Engineering

* GitHub: https://github.com/siddardha-007
* LinkedIn: https://www.linkedin.com/in/siddardha-bangaru/

---

## License

This project is developed for learning, experimentation, and portfolio purposes.
