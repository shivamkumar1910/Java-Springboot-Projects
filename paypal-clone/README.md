# PayPal Clone

A beginner-friendly digital wallet app inspired by PayPal. The project is implemented as a small microservice-style setup with separate backend services for users, wallets, and transactions, plus a React frontend for the dashboard and payment flows.

## Project goal

This app demonstrates the core experience of a wallet application:

- user registration and login
- wallet balance tracking
- adding money
- sending money between users
- transaction history
- dashboard summaries and payment screens

## Current architecture

The active implementation uses three Spring Boot services:

1. User service
   - handles account registration and login
   - stores user data
   - runs on `http://localhost:8081`

2. Wallet service
   - manages wallet balance and deposit actions
   - runs on `http://localhost:8082`

3. Transaction service
   - records payment activity and transaction history
   - runs on `http://localhost:8083`

There is also a legacy `backend/` folder in the project, which appears to be an older single-app version kept as a reference.

## Workspace structure

```text
paypal-clone/
├── README.md
├── pom.xml
├── backend/                 # legacy/reference app
├── frontend/                # React + Vite UI
├── services/
│   ├── user-service/
│   ├── wallet-service/
│   └── transaction-service/
└── target/
```

## Prerequisites

- Java 17+
- Maven
- Node.js 18+
- npm

## Run the backend services

From the project root, start each service in its own terminal:

```bash
cd paypal-clone/services/user-service
mvn spring-boot:run
```

```bash
cd paypal-clone/services/wallet-service
mvn spring-boot:run
```

```bash
cd paypal-clone/services/transaction-service
mvn spring-boot:run
```

## Run the frontend

```bash
cd paypal-clone/frontend
npm install
npm run dev
```

The frontend is typically served on:

- `http://localhost:5173`

## Backend API overview

### User service (`:8081`)

- `POST /api/users/register`
- `POST /api/users/login`

### Wallet service (`:8082`)

- `GET /api/wallet/{userId}`
- `POST /api/wallet/{userId}/add-money`

### Transaction service (`:8083`)

- `POST /api/transactions/send`
- `GET /api/transactions/{userId}`

## Frontend flow

The React app includes pages for:

- login
- registration
- dashboard
- add money
- send money
- transaction view

The app calls the API through a shared axios client located in `frontend/src/api.js` and sends requests to the local backend base URL:

```js
http://localhost:8080/api
```

## Database configuration

Each service uses an in-memory H2 database by default, configured in its `src/main/resources/application.properties` file. The default profile is designed for local testing without requiring a separate database installation.

Example defaults:

- `user-service` -> `jdbc:h2:mem:paypal_clone_users`
- `wallet-service` -> `jdbc:h2:mem:paypal_clone_wallets`
- `transaction-service` -> `jdbc:h2:mem:paypal_clone_transactions`

## Notes

- This is a learning-focused project, so the architecture is intentionally understandable and easy to follow.
- The UI is built with Vite + React and uses a simple local token flow for authenticated requests.
- The various service modules are meant to be run together to simulate a real wallet platform.

## Future improvements

- add JWT validation across services
- connect services more formally with an API gateway or message broker
- improve validation and error handling
- add user search and profile editing
- add richer wallet deductions and transfer rules
