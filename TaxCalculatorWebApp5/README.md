# Tax Calculator Web App

## Features
- Spring Boot backend with JWT authentication
- PostgreSQL integration
- Tax calculation for New and Old Regimes
- React frontend with Material-UI, JWT login, Excel export

## Setup Instructions
1. Install Java 17, Maven, Node.js 20, npm 10
2. Configure PostgreSQL:
   DB: tax_calculator | User: tax_user | Password: tax_pass
3. Build and run:
   mvn clean package
   java -jar target/tax-calculator.jar
4. Access at http://localhost:8080
