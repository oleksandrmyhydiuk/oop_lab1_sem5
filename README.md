# Playroom Console Application

A Java-based console application designed to manage a children's playroom. This project demonstrates core Object-Oriented Programming (OOP) principles, interactive command-line interface (CLI) design, and Unit Testing.

## Features
* **Interactive CLI:** A user-friendly menu to navigate the application.
* **Toy Catalog Management:** Add and remove toys (Cars, Dolls, Balls) with specific properties.
* **Playroom Setup:** Automatically fill the playroom with toys based on a specified budget.
* **Sorting & Searching:** Sort toys by price and find toys within a specific price range.
* **In-Memory Storage:** Efficiently manages data state during application runtime.

## Technologies Used
* **Java 17**
* **Maven** (Build and dependency management)
* **JUnit 5** (Unit testing)
* **Mockito** (Mocking dependencies in UI tests)

## How to Run
Ensure you have Java and Maven installed. Navigate to the project root directory and execute:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.playroom.app.Application"
```

## How to Test
To run the test suite (verifying storage logic and UI interactions), use:

```bash
mvn test
```