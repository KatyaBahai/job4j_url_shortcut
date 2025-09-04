# job4j_url_shortcut

this is a “URL shortener + site-based authentication” API design.

### Core Features
- Site registration with unique credentials
- JWT-based authorization
- URL conversion (long → short)
- Redirect from short URL → original URL (HTTP 302)
- Statistics: track how many times each shortened URL was accessed
  
### Technology Stack
Java 17

Spring Boot 2.7.x

Spring Web (REST)

Spring Security (JWT authentication)

Spring Data JPA / Hibernate

PostgreSQL

Liquibase (DB migrations)

Lombok

Maven
