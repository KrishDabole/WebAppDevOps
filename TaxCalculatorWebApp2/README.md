Complete Tax Calculator project (packaged for VM). Backend is a Spring Boot app with calculator logic.
Note: This package provides a self-contained buildable project. For simplicity the packaged AuthController returns dummy tokens;
to enable DB-backed auth, replace AuthController with one using the AuthService and repositories.

Run backend:
cd backend
mvn clean package -DskipTests
java -jar target/tax-calculator-backend-0.0.1-SNAPSHOT.jar

Run frontend:
cd frontend
npm install
npm start
