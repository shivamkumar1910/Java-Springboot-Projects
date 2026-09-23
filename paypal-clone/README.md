# PayPal Clone

A beginner-friendly payment app inspired by PayPal, designed to feel personal, practical, and realistic enough for a college or portfolio project without becoming too advanced.

## Project goal

This project demonstrates the core flow of a digital wallet application:
- user registration and login
- wallet balance tracking
- adding money
- sending money between people
- transaction history
- a simple frontend dashboard

This workspace was built to look handmade and intentional rather than like a generic starter template.

## Architecture

The project is organized into three Spring Boot services:

1. User Service
   - handles registration and login
   - stores user data
   - runs on port 8081

2. Wallet Service
   - manages wallet balance
   - handles adding money
   - runs on port 8082

3. Transaction Service
   - records payment activity
   - stores money transfer history
   - runs on port 8083

This keeps the project simple, realistic, and beginner-friendly while still matching the idea of separate payment-domain services.

## Workspace structure

```text
Paypal Clone/
├── README.md
├── pom.xml
├── services/
│   ├── user-service/
│   ├── wallet-service/
│   └── transaction-service/
├── frontend/
│   ├── src/
│   ├── package.json
│   └── ...
└── backend/
    └── legacy-version/
```

## Database setup

The project uses MySQL by default.

Create these databases:

```sql
CREATE DATABASE paypal_clone_users;
CREATE DATABASE paypal_clone_wallets;
CREATE DATABASE paypal_clone_transactions;
```

## Run the services

From the project root:

```bash
mvn clean install
cd services/user-service
mvn spring-boot:run
```

Open a second terminal:

```bash
cd services/wallet-service
mvn spring-boot:run
```

Open a third terminal:

```bash
cd services/transaction-service
mvn spring-boot:run
```

## Frontend setup

```bash
cd frontend
npm install
npm run dev
```

## API overview

### User service
- POST /api/users/register
- POST /api/users/login

### Wallet service
- GET /api/wallet/{userId}
- POST /api/wallet/{userId}/add-money

### Transaction service
- POST /api/transactions/send
- GET /api/transactions/{userId}

## Notes

This project is intentionally simple and clear. The focus is on showing the real logic behind a digital wallet app while keeping the code easy to understand for a beginner developer.

## Future improvements

- add JWT authentication across services
- connect services with REST APIs or a message broker
- add wallet deduction on transfers
- add profile editing and user search
- add stronger validation and error handling
