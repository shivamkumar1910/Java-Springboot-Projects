# My Java Spring Boot Learning Workspace

This is the place where I build small but meaningful projects to sharpen my backend, full-stack, and product-thinking skills.

I keep this workspace organized as a personal portfolio of ideas, experiments, and learning builds. Every project here is meant to be readable, practical, and easy to explain in an interview.

## What this workspace is for

- Building real backend features with Java and Spring Boot
- Practicing clean API design, authentication, and database work
- Creating full-stack apps with a React frontend
- Keeping my projects organized in one place as I keep learning
- Turning side projects into portfolio pieces that reflect how I think as a developer

## Current project

### Paypal Clone
- Path: `Paypal Clone/`
- Stack: Java 17, Spring Boot 3, Spring Security, JWT, JPA, H2/PostgreSQL, React + Vite
- Description: A wallet and payment app inspired by PayPal, covering core flows like login, wallet balance, add-money, money transfer, and transaction history.

## How I like to build projects

I prefer projects that are:

- practical rather than just theoretical
- easy to understand without extra explanation
- structured enough to be scalable later
- built with real business flow in mind
- simple enough that the logic is visible and interview-friendly

## Project structure

```text
Java Springboot Projects/
├── README.md
├── Paypal Clone/
│   ├── backend/
│   ├── frontend/
│   └── README.md
├── Project 2/
│   ├── backend/
│   ├── frontend/
│   └── README.md
└── Project 3/
    ├── backend/
    ├── frontend/
    └── README.md
```

## Typical setup pattern

Each project usually follows this format:

- `backend/` for the Spring Boot application
- `frontend/` for React or another UI layer
- `README.md` for project-specific notes and setup steps
- `pom.xml` or `build.gradle` for dependencies
- `.env.example` for configuration values and secrets

## Standards I keep in mind

- Keep the code readable and clean
- Favor clear architecture over unnecessary complexity
- Use environment variables for database and secret config
- Keep features focused on real use cases
- Add tests where they make sense
- Make each project easy to explain in one conversation

## Typical run command

```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend
cd frontend
npm install
npm run dev
```

## Why this matters

This workspace is more than a folder of code. It reflects the way I learn: by building, debugging, improving, and documenting things I actually understand.

The goal is not to collect random projects. The goal is to build a portfolio that shows I can think in systems, solve real problems, and turn ideas into working software.

## Future direction

I plan to keep adding more projects here as I learn more about:

- API design
- payment flows
- security and authentication
- database modeling
- cloud deployment
- more full-stack product ideas

This is a growth workspace, and I want it to keep evolving with my skills.
