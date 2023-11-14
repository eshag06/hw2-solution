# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## Test Cases

### 1. Test Add Transaction With Valid Amount And Category

- **Method:** `testAddTransactionWithValidAmountAndCategory()`
- **Description:**
  - **Tested Components: Controller, Model, View**
  - Adds a transaction with a valid amount and category using the controller.
  - Verifies that the transaction is added to the model and correctly displayed in the view.
  - Checks if the view is updated with the expected data.

### 2. Test Invalid Input Handling

- **Method:** `testInvalidInputHandling()`
- **Description:**
  - **Tested Components: Controller, View**
  - Attempts to add a transaction with an invalid amount or category.
  - Verifies that the controller rejects the invalid input and displays an error message in the view.
  - Checks that the transactions and total remain unchanged in the view.

### 3. Test Filter By Amount

- **Method:** `testFilterByAmount()`
- **Description:**
  - **Tested Components: Controller, View**
  - Adds multiple transactions with different amounts and applies an amount filter through the controller.
  - Verifies that only transactions matching the specified amount are returned and highlighted in the view.

### 4. Test Filter By Category

- **Method:** `testFilterByCategory()`
- **Description:**
  - **Tested Components: Controller, View**
  - Adds multiple transactions with different categories and applies a category filter through the controller.
  - Verifies that only transactions matching the specified category are returned and highlighted in the view.
