# Project Management System

A beginner-friendly Spring Boot REST API for managing users, projects, and tasks. Projects have owners, and tasks can include due dates, priorities, statuses, and optional assignees.

## Tech Stack

- Java 17
- Spring Boot 3.3.5
- Spring Web and Spring Data JPA
- PostgreSQL
- Maven, Lombok, and Jakarta Bean Validation
- Postman for API testing

## Architecture

The application uses a simple layered design:

- `controller`: HTTP endpoints and request validation
- `service`: application rules and resource lookups
- `repository`: Spring Data JPA persistence
- `entity`: database models and enums
- `dto`: request and response records
- `exception`: not-found and global API error handling

## Database Setup

Create the PostgreSQL database:

```sql
CREATE DATABASE project_management;
```

Configure the connection with environment variables from this directory:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/project_management"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
```

For local learning, Hibernate uses `ddl-auto=update` to create or update tables.

## Run

```powershell
cd project-management-system
mvn spring-boot:run
```

The API is available at `http://localhost:8080`.

## API Endpoints

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

## Postman Examples

Set `Content-Type: application/json` for requests with a body.

### Create a user

`POST http://localhost:8080/api/users`

```json
{
	"name": "Ada Lovelace",
	"email": "ada@example.com",
	"role": "MANAGER"
}
```

### Create a project

Use the returned user id as `ownerId`.

`POST http://localhost:8080/api/projects`

```json
{
	"name": "Website redesign",
	"description": "Refresh the public website",
	"status": "ACTIVE",
	"ownerId": 1
}
```

### Create a task

Use the returned project and user ids.

`POST http://localhost:8080/api/tasks`

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

### Get project tasks

`GET http://localhost:8080/api/tasks/project/1`

Missing resources return `404`; invalid request fields return `400` with a JSON error response.

## Example Error Response

```json
{
	"timestamp": "2026-09-23T10:15:30",
	"message": "Project not found",
	"status": 404
}
```

## Interview Questions

1. Why use DTOs instead of returning JPA entities? DTOs keep the API contract separate from database models.
2. What does `@ManyToOne` mean here? Many tasks can belong to one project or be assigned to one user.
3. Why use `FetchType.LAZY`? Related data is loaded only when needed, reducing unnecessary queries.
4. What does `@RestControllerAdvice` do? It turns exceptions into consistent HTTP error responses.
5. Why is `ddl-auto=update` mainly for learning? Production schemas should be managed with controlled migrations.
6. How would you add pagination? Add `Pageable` parameters and return a Spring Data `Page`.
7. How is duplicate email prevented? The user email column has a unique database constraint.
8. Where would authentication fit? Security configuration and authentication services would sit beside the existing application layers.
9. How can services be tested without PostgreSQL? Mock repositories in unit tests and test the service rules directly.
10. What indexes could help later? Index `project_id`, `assignee_id`, status, and due date as query volume grows.
