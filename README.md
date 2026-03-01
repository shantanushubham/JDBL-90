# JDBL-90 Project Repository

Welcome to the main repository for JDBL-90. This workspace contains several projects, spanning across mono-repositories and microservices.

## Services

* **[Food Ordering App](./food-ordering-app)**: A Spring Boot application handling typical food ordering functionalities, including user authentication (Oauth2, JWT), database connection (PostgreSQL), and Redis caching.
* **[My Spring App](./my-spring-app)**: A basic Spring Boot starting application with a PostgreSQL database connected.
* **[Microservices](./microservices)**: A collection of interdependent microservices.
  * **Addition Service**: A Spring Boot application returning the sum of two numbers.
  * **Subtraction Service**: A Node.js application returning the difference of two numbers.
  * **Multiplication Service**: A FastAPI application returning the product of two numbers.
  * **Aggregator Service**: A Spring Boot application calling the three services mentioned above concurrently as well as performing division, returning a consolidated response.

## Getting Started

An `.env` file is required in the root directory for secrets and credentials. Make sure it is present with the correct values.

### Docker Compose
Each Spring Boot app contains a `.yml` file that defines its needed databases. You can start them up easily:

```sh
docker-compose up -d
```
