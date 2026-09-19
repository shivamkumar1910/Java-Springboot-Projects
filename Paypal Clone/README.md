# Digital Wallet Project

A beginner-friendly digital wallet and payment app inspired by fintech dashboards, built with React, Spring Boot, JWT, and PostgreSQL. This project simulates real wallet behavior without using any real payment gateway or real money processing.

## Project Overview

This portfolio project demonstrates:
- React frontend
- Spring Boot backend
- Spring Security + JWT
- JPA/Hibernate with PostgreSQL
- Wallet creation and balance management
- Money transfer between users
- Transaction history
- Profile management
- Basic API security and validation

## Features

- User registration and login
- JWT-based authenticated API access
- Automatic wallet creation after registration
- Add money to wallet
- Search users by username
- Send money to another user
- Transaction history for the logged-in user
- Profile view and update
- Simple dashboard with totals and recent transactions
- Global error handling

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- Maven
- PostgreSQL

### Frontend
- React
- JavaScript
- Vite
- Tailwind CSS
- React Router
- Axios
- Lucide React

## Architecture

The backend is kept intentionally simple:

Controller -> Service -> Repository -> PostgreSQL

The app uses a basic layered design with the main business logic in the service layer.

## Project Structure

```text
Paypal Clone/
├── backend/
│   ├── src/main/java/com/example/wallet/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── entity/
│   │   ├── dto/
│   │   ├── security/
│   │   ├── exception/
│   │   ├── data/
│   │   ├── WalletApplication.java
│   │   └── ...
│   └── pom.xml
├── frontend/
│   ├── src/
│   ├── package.json
│   └── ...
├── README.md
└── .env.example
```

## Database Structure

### User
- id
- name
- username
- email
- password
- createdAt

### Wallet
- id
- user_id
- balance
- currency

### Transaction
- id
- sender_id
- receiver_id
- amount
- message
- type
- status
- createdAt

## API Endpoints

### Auth
- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/logout

### Users
- GET /api/users/me
- GET /api/users/search?username=rahul

### Wallet
- GET /api/wallet
- POST /api/wallet/add-money

### Payments
- POST /api/payments/send

### Transactions
- GET /api/transactions
- GET /api/transactions/{id}

## Environment Variables

Create a `.env` file or use production environment variables with:

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/paypal_clone
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres
JWT_SECRET=your-long-secret-key
```

## Database Setup

1. Install PostgreSQL.
2. Create a database named `paypal_clone`.
3. Update environment variables.
4. Start the Spring Boot app.

## Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

## Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

## How to Run PostgreSQL

On Windows with local PostgreSQL installed:

```bash
pg_ctl -D "C:\Program Files\PostgreSQL\<version>\data" start
```

Then connect using your local PostgreSQL admin or psql client.

## Test Accounts

Development seed accounts are created automatically:

- Alice
  - Username: alice
  - Password: password123
- Bob
  - Username: bob
  - Password: password123

## Future Improvements

- Edit profile password change form
- Better transaction filters
- Search user suggestions
- More dashboard charts
- Email verification flow
- Refresh token support

## Notes

This project is intentionally simple and beginner-friendly. It demonstrates the core idea behind a digital wallet app without using any real payment processing or external payment APIs.
