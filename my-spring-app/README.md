# My Spring App

A basic Spring Boot application template connected to a PostgreSQL database.

## Details
- Configurations are present in `application.yaml` to connect to a default database setup.
- Containerized PostgreSQL is ready via `docker-compose.yml`.

## Execution
Make sure the main `.env` contains the required `POSTGRES_USER` and `POSTGRES_PASSWORD` values to connect.

```sh
# Start up the PostgreSQL container
docker-compose up -d
```
