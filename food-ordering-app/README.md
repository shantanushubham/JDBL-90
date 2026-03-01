# Food Ordering App

A full-fledged Spring Boot backend application handling everything related to food ordering.

## Key Features
- **Security**: Secured endpoints using Spring Security, GitHub OAuth2 provider, and stateless JWT sessions.
- **Database**: Standardized on PostgreSQL database (runs on port 5433 via `docker-compose`).
- **Caching**: Employs Redis for performant caching functionalities.
- **Data Access**: Utilizes Spring Data JPA along with Hibernate for seamless entities management.

## Setup
Please make sure the environment variables are correctly loaded in the parent project's `.env` file before starting up the repository.

```sh
# Start PostgreSQL and Redis via Docker
docker-compose up -d
```
