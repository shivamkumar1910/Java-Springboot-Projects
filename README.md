# Java Spring Boot Projects

A learning workspace containing small, practical Java and Spring Boot applications. Each project is designed to be readable, runnable locally, and easy to explain in an interview.

## Projects

### PayPal Clone

- Location: [paypal-clone](paypal-clone/)
- Stack: Java 17, Spring Boot, Spring Security, JWT, JPA, PostgreSQL/H2, React, and Vite
- Focus: wallet balance, adding money, transfers, authentication, and transaction history

### Project Management System

- Location: [project-management-system](project-management-system/)
- Stack: Java 17, Spring Boot, Spring Web, Spring Data JPA, PostgreSQL, Maven, Lombok, and Bean Validation
- Focus: users, projects, project ownership, task assignment, due dates, priorities, and task status
- API: REST endpoints under `/api/users`, `/api/projects`, and `/api/tasks`

## Workspace Layout

```text
Java-Springboot-Projects/
├── README.md
├── paypal-clone/
│   ├── backend/
│   ├── frontend/
│   └── README.md
└── project-management-system/
    ├── pom.xml
    ├── src/
    └── README.md
```

## Common Standards

- Keep business logic in services and database access in repositories.
- Use DTOs for API input and output.
- Externalize database credentials with environment variables.
- Prefer simple, focused features over unnecessary infrastructure.
- Document setup, API examples, and testing steps in each project README.

## Running a Spring Boot Project

From the selected project directory, run:

```bash
mvn spring-boot:run
```

Refer to the project-specific README for database setup, ports, frontend commands, and API examples.
