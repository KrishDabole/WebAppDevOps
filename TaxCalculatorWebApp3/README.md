One-step build Tax Calculator (Vite frontend built during Maven package) - Full guide for Ubuntu 22.04 LTS


Step 0: Create Ubuntu 22.04 VM in VMware Workstation 17 Pro
- Allocate: 4 vCPU, 8 GB RAM, 60 GB disk (thin), NAT network or bridged.
- Mount Ubuntu 22.04.5 desktop ISO and install.

Step 1: Update system
sudo apt update && sudo apt upgrade -y

Step 2: Install essentials
sudo apt install -y curl build-essential git unzip

Step 3: Install Java 17
sudo apt install -y openjdk-17-jdk
java -version
# should show OpenJDK 17

Step 4: Install Maven 3.8+
sudo apt install -y maven
mvn -v

Step 5: Install Node (we will use Node 20 via NodeSource)
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs
node -v
npm -v

Step 6: Install PostgreSQL
sudo apt install -y postgresql postgresql-contrib
sudo -i -u postgres psql
CREATE DATABASE tax_calculator;
CREATE USER tax_user WITH ENCRYPTED PASSWORD 'tax_pass';
GRANT ALL PRIVILEGES ON DATABASE tax_calculator TO tax_user;
\q

Step 7: Import project into VM
# copy/unzip the project here, e.g. /home/ubuntu/projects/tax
unzip tax-calculator-one-step.zip -d /home/ubuntu/projects/
cd /home/ubuntu/projects/tax-calculator-one-step

Step 8: Edit JWT secret (important)
# Edit backend/src/main/resources/application.properties and change security.jwt.secret to a long random string

Step 9: Build (one-step)
# This will install node/npm locally (via frontend-maven-plugin), run npm install and npm run build, copy dist into backend static and package JAR
cd backend
mvn clean package -DskipTests

# final jar will be at backend/target/tax-calculator-one-step-0.0.1-SNAPSHOT.jar

Step 10: Run
java -jar target/tax-calculator-one-step-0.0.1-SNAPSHOT.jar
# Open http://localhost:8080

Troubleshooting tips:
- If frontend build fails due to node version, ensure curl command worked and you have internet access.
- If Maven fails to download plugins, check proxy/firewall.
- Check backend logs for DB connection errors; ensure PostgreSQL is running and credentials match application.properties.

Security note: Replace JWT secret before deploying to production and use strong passwords for DB.
