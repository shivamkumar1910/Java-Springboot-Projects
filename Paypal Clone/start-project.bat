@echo off
cd /d "%~dp0"

start "User Service" cmd /k "cd /d "%~dp0services\user-service" && set PATH=C:\Users\Dell\maven\apache-maven-3.9.16\bin;%PATH% && mvn spring-boot:run"
start "Wallet Service" cmd /k "cd /d "%~dp0services\wallet-service" && set PATH=C:\Users\Dell\maven\apache-maven-3.9.16\bin;%PATH% && mvn spring-boot:run"
start "Transaction Service" cmd /k "cd /d "%~dp0services\transaction-service" && set PATH=C:\Users\Dell\maven\apache-maven-3.9.16\bin;%PATH% && mvn spring-boot:run"

echo All services are starting...
