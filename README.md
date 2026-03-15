# 📚 Book Management System - BMS

A production-ready **RESTful API** built with **Spring Boot 3** and **Java 17** for managing a book catalogue. Supports full CRUD operations, server-side pagination, multi-field sorting, and flexible search across all book properties.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue?style=flat-square&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.9+-red?style=flat-square&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

---

## 📑 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Book Entity](#book-entity)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [API Reference](#api-reference)
- [Example Requests](#example-requests)
- [HTTP Status Codes](#http-status-codes)
- [Running Tests](#running-tests)
- [Docker](#docker-optional)
- [Future Updates](#future-updates)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

Book Management System is a backend REST API that allows you to manage a collection of books. It is built following a clean **layered architecture** (Controller → Service → Repository) and uses **Spring Data JPA with Hibernate** to interact with a **PostgreSQL** database.

All responses follow a consistent API envelope format. Errors are handled globally via a custom `@ControllerAdvice` exception handler, and the API is fully documented via **Swagger / OpenAPI 3**.

---

## Features

| # | Feature | Description |
|---|---------|-------------|
| ✅ | **CRUD Operations** | Create, Read, Update, and Delete books |
| 📄 | **Pagination** | Page-based results using Spring Data `Pageable` |
| ↕️ | **Sorting** | Sort by any book field in `ASC` or `DESC` order |
| 🔍 | **Search** | Search by title, author, genre, ISBN, publisher, or year |
| 🔗 | **Combined Queries** | Mix search + pagination + sorting in a single request |
| ✔️ | **Validation** | Bean Validation (`@NotBlank`, `@Min`, etc.) with clear error messages |
| 🛡️ | **Error Handling** | Global exception handler with structured JSON error responses |
| 🗄️ | **JPA / Hibernate** | ORM-based persistence with automatic DDL via `ddl-auto` |
| 📑 | **Swagger / OpenAPI** | Auto-generated interactive docs at `/swagger-ui.html` |
| 🧪 | **Unit Tests** | JUnit 5 + Mockito tests for service and controller layers |

---

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 LTS | Core language |
| Spring Boot | 3.2.x | Application framework |
| Spring Data JPA | 3.2.x | Repository abstraction over Hibernate |
| Spring Web (MVC) | 3.2.x | REST controllers and request mapping |
| Hibernate | 6.x | ORM provider and DDL generation |
| PostgreSQL | 15+ | Relational database |
| Maven | 3.9+ | Build and dependency management |
| Lombok | 1.18.x | Boilerplate reduction (`@Getter`, `@Builder`, `@Slf4j`) |
| MapStruct | 1.5.x | Compile-time DTO ↔ Entity mapping |
| SpringDoc OpenAPI | 2.3.x | Swagger UI and OpenAPI 3 spec |
| JUnit 5 + Mockito | 5.x | Unit and integration testing |

---

## Book Entity

All fields are searchable and sortable via query parameters.

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | `Long` | `@Id`, Auto | Primary key, auto-generated |
| `title` | `String` | `@NotBlank` | Title of the book |
| `author` | `String` | `@NotBlank` | Full name of the author |
| `isbn` | `String` | `@NotBlank`, unique | ISBN-10 or ISBN-13 identifier |
| `genre` | `String` | nullable | Genre / category (Fiction, Sci-Fi…) |
| `publisher` | `String` | nullable | Publishing house name |
| `publishedYear` | `Integer` | `@Min(1000)` | Year of publication |
| `price` | `BigDecimal` | `@DecimalMin("0.0")` | Retail price |
| `language` | `String` | nullable | Language the book is written in |
| `pages` | `Integer` | `@Min(1)` | Total page count |
| `description` | `String` | nullable, `@Column(columnDefinition="TEXT")` | Summary of the book |
| `createdAt` | `LocalDateTime` | Auto | Timestamp of record creation |
| `updatedAt` | `LocalDateTime` | Auto | Timestamp of last update |

---

## Prerequisites

Before running the project, make sure you have the following installed:

- **Java 17+** — verify with `java -version`
- **Maven 3.9+** — verify with `mvn -version`
- **PostgreSQL 15+** — running locally or via Docker
- **Git**
- **IDE** — IntelliJ IDEA (recommended) or Eclipse

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/book-management-system.git
cd book-management-system
```

### 2. Create the PostgreSQL Database

```sql
psql -U postgres
CREATE DATABASE book_db;
\q
```

### 3. Configure `application.properties`

Edit `src/main/resources/application.properties`:

```properties
# PostgreSQL DataSource
spring.datasource.url=jdbc:postgresql://localhost:5432/book_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Server
server.port=8080
```

### 4. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The server starts at **http://localhost:8080**  
Swagger UI is available at **http://localhost:8080/swagger-ui.html**

---

## Project Structure

```
src/
└── main/
    ├── java/com/example/bookmanagement/
    │   ├── BookManagementApplication.java     # Entry point
    │   ├── controller/
    │   │   └── BookController.java            # REST endpoints
    │   ├── service/
    │   │   ├── BookService.java               # Interface
    │   │   └── BookServiceImpl.java           # Business logic
    │   ├── repository/
    │   │   └── BookRepository.java            # JPA repository + custom queries
    │   ├── entity/
    │   │   └── Book.java                      # JPA entity
    │   ├── dto/
    │   │   ├── BookRequestDTO.java            # Incoming request body
    │   │   └── BookResponseDTO.java           # Outgoing response body
    │   ├── mapper/
    │   │   └── BookMapper.java                # MapStruct mapper
    │   ├── exception/
    │   │   ├── BookNotFoundException.java
    │   │   └── GlobalExceptionHandler.java    # @ControllerAdvice
    │   └── config/
    │       └── OpenApiConfig.java             # Swagger config
    └── resources/
        └── application.properties
```

---

## API Reference

**Base URL:** `http://localhost:8080/api/v1/books`

### CRUD Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/books` | Get all books (paginated + sorted) |
| `GET` | `/api/v1/books/{id}` | Get a single book by ID |
| `POST` | `/api/v1/books` | Create a new book |
| `PUT` | `/api/v1/books/{id}` | Update all fields of a book |
| `PATCH` | `/api/v1/books/{id}` | Partially update a book |
| `DELETE` | `/api/v1/books/{id}` | Delete a book by ID |

### Pagination & Sorting Query Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | `int` | `0` | Zero-based page index |
| `size` | `int` | `10` | Records per page (max 100) |
| `sort` | `string` | `id,asc` | Field and direction — e.g. `title,asc` or `price,desc` |

### Search Endpoints

| Endpoint | Description |
|----------|-------------|
| `GET /api/v1/books/search?q={keyword}` | Full-text search across all fields |
| `GET /api/v1/books/search/title?title={value}` | Search by title (case-insensitive, partial match) |
| `GET /api/v1/books/search/author?author={value}` | Search by author name |
| `GET /api/v1/books/search/genre?genre={value}` | Filter by genre |
| `GET /api/v1/books/search/isbn?isbn={value}` | Look up a book by ISBN |
| `GET /api/v1/books/search/year?year={value}` | Filter by published year |
| `GET /api/v1/books/search/publisher?publisher={value}` | Filter by publisher |

> All search endpoints also support `page`, `size`, and `sort` query parameters.

---

## Example Requests

### Get page 2, 5 books per page, sorted by title ascending
```
GET /api/v1/books?page=1&size=5&sort=title,asc
```

### Search by author with pagination and sorting
```
GET /api/v1/books/search/author?author=Tolkien&page=0&size=10&sort=publishedYear,desc
```

### Create a new book

```http
POST /api/v1/books
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "genre": "Programming",
  "publisher": "Prentice Hall",
  "publishedYear": 2008,
  "price": 39.99,
  "language": "English",
  "pages": 464,
  "description": "A handbook of agile software craftsmanship."
}
```

### Sample Paginated Response

```json
{
  "content": [
    {
      "id": 1,
      "title": "Clean Code",
      "author": "Robert C. Martin",
      "isbn": "978-0132350884",
      "genre": "Programming",
      "publishedYear": 2008,
      "price": 39.99
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 42,
  "totalPages": 5,
  "last": false
}
```

---

## HTTP Status Codes

| Status Code | Meaning |
|-------------|---------|
| `200 OK` | Successful GET, PUT, PATCH |
| `201 Created` | Book successfully created via POST |
| `204 No Content` | Book successfully deleted |
| `400 Bad Request` | Validation failure — missing fields or bad format |
| `404 Not Found` | Book with the given ID does not exist |
| `409 Conflict` | Duplicate ISBN detected |
| `500 Internal Server Error` | Unhandled server-side error |

---

## Running Tests

```bash
# Run all tests
mvn test

# Run only service layer tests
mvn test -Dtest=BookServiceImplTest

# Run with coverage report (requires JaCoCo plugin)
mvn verify
```

Test reports are generated in `target/surefire-reports/`.  
Coverage reports appear in `target/site/jacoco/` if JaCoCo is configured.

---

## Docker (Optional)

A `docker-compose.yml` is included to spin up the app and PostgreSQL together:

```bash
docker-compose up --build
```

The API will be available at **http://localhost:8080** and PostgreSQL on port **5432**.

---

## Future Updates

Planned features and improvements for upcoming releases:

- [ ] **JWT Authentication & Authorization** — Secure endpoints with role-based access control (Admin, User)
- [ ] **User Management** — Register/login system with user profiles and book ownership
- [ ] **Wishlist / Reading List** — Allow users to bookmark books and track reading progress
- [ ] **Book Reviews & Ratings** — Users can rate books (1–5 stars) and post reviews
- [ ] **Advanced Filtering** — Filter by price range, page count range, language, and multiple genres simultaneously
- [ ] **Image Upload** — Upload and serve book cover images via AWS S3 or local storage
- [ ] **Bulk Import** — Import book data from CSV / Excel files
- [ ] **Export to PDF / CSV** — Export book listings with applied filters
- [ ] **Audit Logging** — Track who created or modified a record and when
- [ ] **Caching** — Redis-based caching for frequently queried books and search results
- [ ] **Rate Limiting** — Prevent API abuse with request throttling
- [ ] **Email Notifications** — Notify users of new arrivals in their preferred genre
- [ ] **GraphQL Support** — Alternative GraphQL endpoint alongside REST
- [ ] **Kubernetes Deployment** — Helm charts and K8s manifests for production deployment
- [ ] **CI/CD Pipeline** — GitHub Actions workflow for automated build, test, and deploy

---

## Contributing

Contributions are welcome and appreciated!

1. Fork the repository
2. Create a feature branch
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Write tests for your changes
4. Ensure all tests pass
   ```bash
   mvn verify
   ```
5. Commit with a clear message
   ```bash
   git commit -m "feat: add your feature description"
   ```
6. Push and open a Pull Request

Please follow the existing code style and include Javadoc for public methods.

---

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for full details.

---

<p align="center">Built with ☕ Java & 🍃 Spring Boot &nbsp;|&nbsp; Book Management System © 2024</p>
