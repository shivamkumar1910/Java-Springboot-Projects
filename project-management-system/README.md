# Project Management System

A Spring Boot REST API for managing users, projects, and tasks. The app models a practical project-tracking workflow where users own projects and tasks can have due dates, priorities, statuses, and assignees.

## Tech stack

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- Jakarta Bean Validation

## Architecture

The project follows a simple layered design:

- `controller`: REST endpoints and request validation
- `service`: business logic and data coordination
- `repository`: Spring Data JPA persistence layer
- `entity`: database models and enum values
- `dto`: request and response objects
- `exception`: custom exceptions and global API error handling

## Database setup

Create the database locally:

```sql
CREATE DATABASE project_management;
```

Then set the connection environment variables before running the app:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/project_management"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
```

The application uses Hibernate `ddl-auto=update` for local learning and quick iteration.

## Run the app

```powershell
cd project-management-system
mvn spring-boot:run
```

The API runs on:

- `http://localhost:8080`

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/users` | List users |
| GET | `/api/users/{id}` | Get one user |
| POST | `/api/users` | Create a user |
| PUT | `/api/users/{id}` | Update a user |
| DELETE | `/api/users/{id}` | Delete a user |
| GET | `/api/projects` | List projects |
| GET | `/api/projects/{id}` | Get one project |
| POST | `/api/projects` | Create a project |
| PUT | `/api/projects/{id}` | Update a project |
| DELETE | `/api/projects/{id}` | Delete a project |
| GET | `/api/tasks` | List tasks |
| GET | `/api/tasks/{id}` | Get one task |
| GET | `/api/tasks/project/{projectId}` | List tasks in a project |
| POST | `/api/tasks` | Create a task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |

## Example requests

Set `Content-Type: application/json` for requests that include a body.

### Create a user

```http
POST http://localhost:8080/api/users
```

```json
{
  "name": "Ada Lovelace",
  "email": "ada@example.com",
  "role": "MANAGER"
}
```

### Create a project

```http
POST http://localhost:8080/api/projects
```

```json
{
  "name": "Website redesign",
  "description": "Refresh the public website",
  "status": "ACTIVE",
  "ownerId": 1
}
```

### Create a task

```http
POST http://localhost:8080/api/tasks
```

```json
{
  "title": "Create wireframes",
  "description": "Prepare the first design draft",
  "dueDate": "2026-10-15",
  "priority": "HIGH",
  "status": "TODO",
  "projectId": 1,
  "assigneeId": 1
}
```

### Get tasks for a project

```http
GET http://localhost:8080/api/tasks/project/1
```

Missing resources return `404`, and invalid input usually results in a `400` with a JSON error payload.

## Example error response

```json
{
  "timestamp": "2026-09-23T10:15:30",
  "message": "Project not found",
  "status": 404
}
```

## Interview-style questions

1. Why use DTOs instead of returning JPA entities? DTOs keep the API contract separate from persistence details.
2. What does `@ManyToOne` mean here? Many tasks can belong to one project or be assigned to one user.
3. Why use `FetchType.LAZY`? It avoids loading related data unless it is needed.
4. What does `@RestControllerAdvice` do? It centralizes exception handling and produces consistent HTTP responses.
5. Why is `ddl-auto=update` mostly for learning? Production schema changes should be managed with controlled migrations.
6. How would you add pagination? Use `Pageable` and return a Spring Data `Page`.
7. How is duplicate email prevented? Add a unique database constraint and validation logic.
8. Where would authentication fit? In a security configuration and authentication service alongside the current layers.
9. How can services be tested without PostgreSQL? Use mock repositories and test the service rules directly.
10. Which indexes would help later? Add indexes for project ID, assignee ID, status, and due date as usage grows.
