# Library Management System 2.0

A full-featured library management application built with Java and Spring Boot. The project aims to simulate a real-world software system by integrating Object-Oriented Programming, database management, REST API development, user interfaces, security, and automated testing.

## 1. Project Overview

Library Management System 2.0 is an application designed to manage the daily operations of a library.

It provides functionality for managing books, library members, borrowing and returning books, tracking overdue loans, and managing user access.

Unlike a basic OOP exercise, this project focuses on building a complete software application with multiple interconnected components and a maintainable architecture.

## 2. Main Objectives

- Apply OOP principles in a real-world application.
- Develop a layered software architecture.
- Integrate a relational database.
- Build and consume REST APIs.
- Develop a graphical web interface.
- Implement authentication and authorization.
- Practice unit, integration, and API testing.
- Apply software engineering principles and best practices.
- Learn application deployment and containerization.

## 3. Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java 21 |
| Backend Framework | Spring Boot |
| Database | PostgreSQL |
| ORM | Hibernate / Spring Data JPA |
| API | Spring Web (REST) |
| Frontend | Thymeleaf, HTML, CSS, JavaScript |
| Security | Spring Security |
| Build Tool | Maven |
| Unit Testing | JUnit 5, Mockito |
| Integration Testing | Spring Boot Test, Testcontainers |
| Database Migrations | Flyway |
| API Documentation | OpenAPI / Swagger |
| Containerization | Docker |
| Version Control | Git / GitHub |
| CI/CD | GitHub Actions |

## 4. Core Features

### 4.1 Authentication and Authorization
- User registration and login.
- Password hashing.
- Role-based access control.
- Admin and librarian permissions.
- Secure access to protected operations.

### 4.2 Book Management
- Add, remove, and update books.
- Retrieve book information.
- Search and filter books.
- Categorize books by genre.
- Track total and available copies.

### 4.3 Member Management
- Register library members.
- Update member information.
- View member profiles.
- Track borrowing history.
- Manage membership status.

### 4.4 Loan Management
- Borrow books.
- Return borrowed books.
- Calculate overdue periods.
- Track active and completed loans.
- Enforce borrowing limits.
- Prevent borrowing unavailable books.

### 4.5 Dashboard
- Total number of books.
- Registered members.
- Currently borrowed books.
- Overdue loans.
- Recently added books.

### 4.6 REST API
- CRUD operations.
- JSON request and response handling.
- Input validation.
- HTTP status code handling.
- Centralized error responses.
- API documentation.

## 5. Database Design

The initial database will contain the following principal entities:

| Entity | Description |
|---|---|
| Book | Stores book information and inventory. |
| Member | Stores library member information. |
| User | Stores application accounts and roles. |
| Loan | Records borrowing and returning transactions. |
| Category | Stores book categories. |

Relationships will be implemented using primary keys, foreign keys, and appropriate relational constraints.

Additional entities and relationships may be introduced as the application evolves.

## 6. Project Architecture

The application will follow a layered architecture:

- **Domain:** Entities, enums, business rules, and domain exceptions.
- **Repository:** Database access and persistence operations.
- **Service:** Business logic and application operations.
- **Controller:** HTTP request handling and REST endpoints.
- **DTO:** Data transfer objects for API communication.
- **Mapper:** Conversion between entities and DTOs.
- **Config:** Application and framework configuration.
- **Security:** Authentication and authorization.
- **Exception:** Centralized exception handling.

The application will initially follow a modular monolithic architecture rather than a microservices architecture.

## 7. Testing Strategy

Testing will be integrated throughout development.

### Unit Testing
- Test individual methods and classes.
- Test business rules.
- Test validation and exception handling.
- Use JUnit 5 and Mockito.

### Integration Testing
- Test interactions between application layers.
- Test database persistence.
- Test transactions.
- Use Spring Boot Test and Testcontainers.

### API Testing
- Test HTTP endpoints.
- Verify request validation.
- Verify response status codes and JSON structures.
- Test authentication and authorization.

### Additional Testing
- Boundary value analysis.
- Positive and negative testing.
- Equivalence partitioning.
- Regression testing.

## 8. Development Roadmap

The project will be developed incrementally.

- [ ] **Phase 1:** Project setup and environment configuration.
- [ ] **Phase 2:** PostgreSQL integration and database design.
- [ ] **Phase 3:** Entities, repositories, and business logic.
- [ ] **Phase 4:** DTOs, mappers, and REST API development.
- [ ] **Phase 5:** Web interface development.
- [ ] **Phase 6:** Authentication and authorization.
- [ ] **Phase 7:** Unit and integration testing.
- [ ] **Phase 8:** API documentation and error handling.
- [ ] **Phase 9:** Docker and application deployment.
- [ ] **Phase 10:** CI/CD and final improvements.

## 9. Development Principles

The following principles will guide implementation:

1. Maintain separation of concerns.
2. Apply SOLID principles where appropriate.
3. Avoid unnecessary complexity and overengineering.
4. Write meaningful and maintainable code.
5. Integrate testing throughout development.
6. Use Git for version control.
7. Document important architectural decisions.
8. Prioritize correctness, reliability, and maintainability.

## 10. Future Improvements

Potential extensions include:

- Book reservation and waiting lists.
- Email notifications.
- Fine calculation and payment tracking.
- Advanced search and filtering.
- Analytics and statistical reports.
- External book information API integration.
- Automated database backups.
- Frontend migration to React or another dedicated frontend framework.

## 11. Project Philosophy

This project is intended to be a practical software engineering learning experience.

The primary objective is not simply to make an application that works, but to understand how its components interact, how software architecture is designed, how data is managed, how functionality is tested, and how an application can be maintained and extended.

**Build incrementally. Understand deeply. Test continuously. Improve constantly.**