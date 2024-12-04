# Test Project QA

## Description

This project is an **automated test** that interacts with the **SportsData API** to retrieve NBA player 
data and verify the 3-pointers average for the last 5 games for each player.

## Features
- API Integration: Fetches NBA player data using the SportsData API.
- Selenium WebDriver: Automates the browser for testing.
- JUnit: Runs tests on the project.
- CI/CD Pipeline: Integrated into GitHub Actions for continuous integration and delivery.
- Logging: Uses SLF4J and Logback for logging purposes.

### Test Details
The test verifies:

- Active Players Only: The API response contains data only for players marked as "Active."
- Player Name Mapping: The player’s full name is correctly parsed and mapped.
- Valid NBA ID: Each player has a valid NBA ID assigned to them.
- 3-Point Average Check: Players are validated for 3PM average in the last 5 games is greater
- than 1 for the last 5 games.

## Requirements
- **Java 8** 
- **Maven** 
- **Selenium WebDriver** 
- **JUnit 5** 

## Getting Started
#### Clone the Repository
```bash
git clone https://github.com/leamoonmoon/testProjectQA.git
cd testProjectQA
```
#### Install Dependencies
```bash
mvn clean install
```

#### Running Tests Locally
```bash
mvn test
```

