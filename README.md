# Java Spring Boot Projects

This workspace contains two practical Java/Spring Boot learning projects built for local development and portfolio demos.

## Included projects

### 1. PayPal Clone

- Location: [paypal-clone](paypal-clone/)
- Stack: Java 17, Spring Boot, Spring Data JPA, Spring Security, React, Vite, and Maven
- Focus: user authentication, wallet balances, adding money, sending funds, and transaction tracking
- Main services: `user-service`, `wallet-service`, and `transaction-service`

### 2. Project Management System

- Location: [project-management-system](project-management-system/)
- Stack: Java 17, Spring Boot, Spring Web, Spring Data JPA, PostgreSQL, Maven, Lombok, and validation
- Focus: users, projects, task assignment, priorities, due dates, and status tracking
- API base path: `/api/users`, `/api/projects`, and `/api/tasks`

## Workspace structure

```text
Java-Springboot-Projects/
├── README.md
├── paypal-clone/
│   ├── backend/            # legacy/reference backend app
│   ├── frontend/           # React + Vite app
│   ├── services/
│   │   ├── user-service/
│   │   ├── wallet-service/
│   │   └── transaction-service/
│   ├── pom.xml
│   └── README.md
├── project-management-system/
│   ├── src/
│   ├── pom.xml
│   └── README.md
└── .gitignore
```

## Quick start

### Run a Spring Boot app

```bash
cd <project-folder>
mvn spring-boot:run
```

### Run the PayPal frontend

```bash
cd paypal-clone/frontend
npm install
npm run dev
```

### Run the project management app

```bash
cd project-management-system
mvn spring-boot:run
```

## Notes

- Use the project-specific README in each folder for exact setup, ports, and API examples.
- Keep credentials and environment variables outside the source code when possible.
- Each project is intentionally simple and beginner-friendly, with a focus on readability and learning value.
