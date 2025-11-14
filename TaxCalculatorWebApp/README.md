Tax Calculator Web App (React + Spring Boot + PostgreSQL)

Run on Ubuntu VM:

1) Install:
sudo apt update
sudo apt install openjdk-17-jdk maven nodejs npm postgresql -y

2) Setup DB:
sudo -i -u postgres psql
CREATE DATABASE tax_calculator;
CREATE USER tax_user WITH ENCRYPTED PASSWORD 'tax_pass';
GRANT ALL PRIVILEGES ON DATABASE tax_calculator TO tax_user;
\q

3) Configure (if needed) backend/src/main/resources/application.properties

4) Build & run backend:
cd backend
mvn clean package -DskipTests
java -jar target/tax-calculator-backend-0.0.1-SNAPSHOT.jar

5) Run frontend:
cd frontend
npm install
npm start
