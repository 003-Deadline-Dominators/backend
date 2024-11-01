# CodeCraft Backend

## Description
CodeCraft's backend is built with Spring Boot 3, providing robust server-side functionality for the CodeCraft learning platform. It handles user data management, coding challenge generation, and integration with various services including MySQL database and Gemini LLM API.

## Required Software
- MySQL (database)
- Docker (for Python environment)
- Java Development Kit (JDK)
- Maven
- Git (optional - for version control)

## Getting Started
1. Clone the backend repository
2. Pull the required Docker image:
   ```bash
   docker pull rita6667/gemini-app:latest
   ```
3. Ensure Docker is running

### Running the Backend (make sure it's in the backend directory)
1. Build the project:
   ```bash
   mvn clean package -DskipTests
   ```
2. Navigate to the target directory:
   ```bash
   cd UserDatabase/target
   ```
3. Run the JAR file:
   ```bash
   java -jar UserDatabase-0.0.1-SNAPSHOT.jar --server.port=8080
   ```
   The backend service will start on port 8080.

## Deployment
1. Generate JAR file:
   ```bash
   ./mvnw clean package -DskipTests
   ```
2. Upload the packaged JAR file to your hosting platform
3. Note: Update any `localhost` references in the code to your hosting platform's public IP address

## Tech Stack
- Spring Boot 3
- MySQL
- Gemini LLM API
- Docker
- JUnit (Testing)

## Project Structure

### Main Application Entry
- Location: `UserDatabase/src/main/java/com/parsons/ParsonsApp.java`
- Description: Primary Spring Boot application class that initializes the server and database connections

### Configuration
- Location: `UserDatabase/src/main/resources/application.yml`
- Description: Contains essential configurations for:
  - Database connection
  - Server properties
  - MyBatis framework settings

## Security and Ethics
- User Privacy Protection with anonymized IP addresses
- Secure, encrypted database storage
- Ethical AI content generation
- Equitable access and open-source compliance

## Testing
Backend tests are located in `UserDatabase/src/test/java/com/parsons/test`

## Contributors
- Howard Li (howard3@student.unimelb.edu.au)
- Yiru Liu (yirul6@student.unimelb.edu.au)
- Yan Gong (yagong1@student.unimelb.edu.au)
- Jiayi Wang (jiawang10@student.unimelb.edu.au)
- Yifan Zhang (yifanzhang@student.unimelb.edu.au)

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Links
- Website: http://54.252.5.239/
- Confluence: https://student-team-zjyi1h63.atlassian.net/wiki/spaces/0D/overview (Requires permission)
