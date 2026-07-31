# Smart Expense Tracker

A simple and efficient backend service to track, filter, and manage personal expenses, built with Java and Spring Boot.

## Features

- ✔ Add Expense
- ✔ Get All Expenses
- ✔ Filter Expenses by Category
- ✔ Delete Expense
- ✔ Calculate Total Expenses
## Tech Stack

- Java 21
- Spring Boot
- Gradle
- JUnit 5
- Mockito
- Lombok
## Prerequisites

- Java 21
- Git
## Installation

### Clone

```bash
git clone https://github.com/rakshitsaxena07/smart-expense-tracker.git
```

### Install

Since the Gradle Wrapper is present, no separate Gradle installation is needed:

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

After the application starts successfully, the API will be available at:

```text
http://localhost:8080
```

### Run Tests

```bash
./gradlew test
```

## Storage

This project uses an in-memory `HashMap` to store expenses. Data will be lost when the application stops.

## API Endpoints

| Method | Endpoint                        | Description                     |
|--------|----------------------------------|----------------------------------|
| POST   | `/expenses`                     | Create a new expense             |
| GET    | `/expenses`                     | Retrieve all expenses            |
| GET    | `/expenses?category=FOOD`       | Retrieve expenses by category    |
| DELETE | `/expenses/{id}`                | Delete an expense                |
| GET    | `/expenses/total`               | Calculate total expenses         |
| GET    | `/expenses/total?category=FOOD` | Calculate total by category      |

## Project Structure

```
src/
 ├── main/
 │   ├── controller
 │   ├── dto
 │   ├── exception
 │   ├── model
 │   ├── repository
 │   └── service
 └── test/
```

## Testing

Unit tests have been written for the service and controller layers using JUnit 5 and Mockito.

 