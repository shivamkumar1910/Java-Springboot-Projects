# PayPal Clone Frontend

This folder contains the React + Vite frontend for the PayPal clone application. It provides the user-facing dashboard, authentication flow, wallet balance display, and money transfer screens.

## Stack

- React 19
- Vite
- React Router
- Axios
- Tailwind CSS
- Lucide icons

## Run locally

From this directory:

```bash
npm install
npm run dev
```

The app will usually run at:

- `http://localhost:5173`

## Build for production

```bash
npm run build
```

## Lint

```bash
npm run lint
```

## Main pages

- login
- register
- dashboard
- add money
- send money

## API configuration

The frontend uses a shared API client in `src/api.js`:

```js
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});
```

This means the frontend expects the backend API to be running locally before starting the UI. In the current project setup, the backend services are exposed on ports `8081`, `8082`, and `8083`, and the UI is intended to work with the local API gateway layer or a matching proxy configuration.

## Notes

- This frontend is intentionally lightweight and easy to follow for a portfolio or learning project.
- Styling is handled with Tailwind and a simple custom design system.
- The app is designed to demonstrate wallet flows rather than production-grade security or architecture.
