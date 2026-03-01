# Aggregator Service

This is a Spring Boot microservice acting as an "aggregator" that coordinates requests to multiple downstream services to perform arithmetic operations.

## Functionality
- Accepts two input numbers.
- Concurrently queries the **Addition**, **Subtraction**, and **Multiplication** services.
- Performs a division operation directly on the input numbers.
- Aggregates the results (Sum, Difference, Product, Quotient) into a consolidated response payload.
