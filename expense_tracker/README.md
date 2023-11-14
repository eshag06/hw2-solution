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

## New Functionality

As we are using the all the functionalites from HW2. Hence, we already have filtering component functionalities based on amount and category. Now, based on that we have developed test cases. The new functionality we added was undo functionality, where the following steps were incorporated : 
1. To get index of a table row and to enable undo button, we have added a `listner` on the table rows.
2. Intially `undoButton` should remain disabled. Therefore, when a table is empty or no filter is applied, then undo button is in disable mode. It gets enabled when a transaction list is selected.
3. We can select a single or multiple rows through the listener. 
4. To perform the undo functionality, we have undo button in the view. Additionally, we have `enableUndoButton` and `disableUndoButton` based on if the undo button is enabled or disabled.
5. Similarly, we have `applyUndo` and `refreshUndoButton` control in controller to perform undo operation.
6. From the model, we get selected rows for which we apply listener. 
7. Now, we have `undoButton` listner to perform undo operation on selected table rows. 
8. When undo operation is performed on some table row using undoButton from the view, the `applyUndo` from controller is called, then first it gets all the selected list from the model using `model.getTransactions` and it removes the transaction from the table row using `model.removeTransaction` and updates the table.

## Test Cases

### 1. Test Add Transaction With Valid Amount And Category

- **Method:** `testAddTransactionWithValidAmountAndCategory()`
- **Description:**
  - **Tested Components: Controller, Model, View**
  - Adds a transaction with a valid amount and category using the controller.
  - Verifies that the transaction is added to the model and correctly displayed in the view.
  - Checks if the view is updated with the expected data, including timestamp validation.

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

### 5. Undo Disallowed

- **Method:** `testUndoDisallowed()`
- **Description:**
  - **Tested Components: Model, View, Controller**
  - Using model it checks the undo button which should be disabled when transaction list is empty.
  - Using view it checks that no transaction is selected when undo button is disabled.

### 6. Undo Allowed

- **Method:** `testUndoAllowed()`
- **Description:**
  - **Tested Components: Model, View, Controller**
  - Checks first through view if transaction list is empty and then multiple transactions are added through controller.
  - A transaction is selected to be deleted and which is deleted through controller via undo functionality. 
  - Gets update transaction list and it was verified confirming the undo function were performed.