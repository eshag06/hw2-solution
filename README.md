# Solution files for hw2

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