# Playroom Console Application

[![Java CI with Maven](https://github.com/oleksandrmyhydiuk/oop_lab1_sem5/actions/workflows/ci.yml/badge.svg)](https://github.com/oleksandrmyhydiuk/oop_lab1_sem5/actions/workflows/ci.yml)

A Java console application designed to prepare and manage a children's playroom across different age groups within an allocated budget and fixed toy capacity. Built to demonstrate core Object-Oriented Programming (OOP) principles, clean package architecture, adherence to Oracle Java Code Conventions, and comprehensive unit testing with JUnit 5 and Mockito.

## Features
* **OOP Domain Hierarchy:** Abstract base class `Toy` with concrete subclasses representing related toy groups (`Car`, `Doll`, `Ball`, `Blocks`), categorized by `AgeGroup` (`BABY`, `TODDLER`, `PRESCHOOL`, `SCHOOL`) and `Size` (`SMALL`, `MEDIUM`, `LARGE`).
* **File-Based Initialization:** Automatically loads the initial toy catalog from a CSV configuration file (`src/main/resources/toys.csv`) or external file path, with a built-in fallback catalog.
* **Playroom Preparation:** Populates the playroom for children of different age groups with a fixed maximum number of toys within the allocated budget.
* **Sorting & Range Search:** Sorts toys in the playroom by price and filters toys matching a specified price range.
* **Minimal Interactive CLI:** Clean, intuitive console menu for managing the catalog and configuring the playroom.

## Project Structure
```text
src/
├── main/
│   ├── java/com/playroom/
│   │   ├── app/
│   │   │   └── Application.java          # Application entry point and main loop
│   │   ├── model/
│   │   │   ├── AgeGroup.java             # Target age groups (BABY, TODDLER, PRESCHOOL, SCHOOL)
│   │   │   ├── Size.java                 # Physical toy sizes (SMALL, MEDIUM, LARGE)
│   │   │   ├── Toy.java                  # Abstract base class for all toys
│   │   │   ├── Car.java                  # Toy car (small, medium, large; electric/mechanical)
│   │   │   ├── Doll.java                 # Doll with material specification
│   │   │   ├── Ball.java                 # Ball with sport type specification
│   │   │   └── Blocks.java               # Building blocks (cubes) with piece count
│   │   ├── storage/
│   │   │   └── PlayroomStorage.java      # Catalog, file loader, budget/capacity setup, sorting & search
│   │   └── ui/
│   │       ├── UserInput.java            # Input reader abstraction for CLI and testability
│   │       ├── ToyMenu.java              # Catalog management menu
│   │       └── PlayroomMenu.java         # Playroom preparation, sorting, and search menu
│   └── resources/
│       └── toys.csv                      # Default initialization parameters for the toy catalog
└── test/
    └── java/com/playroom/
        ├── model/
        │   └── ToyModelTest.java         # Unit tests for domain models and polymorphism
        ├── storage/
        │   └── PlayroomStorageTest.java  # Unit tests for storage, file loading, budget & sorting
        └── ui/
            ├── PlayroomMenuTest.java     # Mockito unit tests for PlayroomMenu
            └── ToyMenuTest.java          # Mockito unit tests for ToyMenu
```

## Technologies Used
* **Java 17**
* **Maven** (Build & dependency management)
* **JUnit 5 (Jupiter)** (Unit testing)
* **Mockito 5** (Mocking dependencies in UI tests)
* **GitHub Actions** (Continuous Integration for automated test runs)

## How to Run
Ensure you have Java 17+ and Maven installed. From the project root directory, run:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.playroom.app.Application"
```

## How to Run Tests
To execute the complete JUnit 5 and Mockito unit test suite:

```bash
mvn test
```

## License
This project is licensed under the [MIT License](LICENSE).
