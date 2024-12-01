# Overview

This project is a Spring Boot application designed to simulate a cryptocurrency trading platform. It allows users to trade cryptocurrencies, view the latest price information, and manage their wallet balances.

---

## Table of Contents

- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Testing the API](#testing-the-api)

---

## Installation

To set up the project, ensure that you have the necessary tools and dependencies installed. Then, follow these steps:

1. Navigate to the project directory using the terminal.
2. Run the following command to build the project and install required dependencies:

    ```bash
    mvn clean install
    ```

---

## Running the Application

Once the project has been successfully built, you can run the application by executing the following command in your terminal:

```bash
    mvn spring-boot:run
   ```

---

## Testing the API

A Postman collection, **Binance App**, has been provided to assist in testing the API endpoints. The collection is exported as a JSON file, which can be found in the `src/main/resources` directory.

### Steps to test the API:

1. Import the provided Postman collection into your local Postman instance.
2. Start the application by running the following command in your terminal:

    ```bash
    mvn spring-boot:run
    ```

3. Use the predefined list of API calls in the Postman collection to interact with the application and verify the results.
poss