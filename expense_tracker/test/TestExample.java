// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

import java.util.Date;
import java.time.Instant;
import java.util.List;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;

import org.junit.Before;
import org.junit.Test;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

  private Color getCellBackgroundColor(JTable table, int row, int column) {
        Component cellRenderer = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
        return cellRenderer.getBackground();
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }


    public void checkTransaction(double amount, String category, Transaction transaction) {
	    assertEquals(amount, transaction.getAmount(), 0.01);
        assertEquals(category, transaction.getCategory());
        String transactionDateString = transaction.getTimestamp();
        Date transactionDate = null;
        try {
            transactionDate = Transaction.dateFormatter.parse(transactionDateString);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
            transactionDate = null;
        }
        Date nowDate = new Date();
        assertNotNull(transactionDate);
        assertNotNull(nowDate);
        // They may differ by 60 ms
        assertTrue(nowDate.getTime() - transactionDate.getTime() < 60000);
    }


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
	    double amount = 50.0;
	    String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Post-condition: List of transactions contains only
	//                 the added transaction	
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
	    Transaction firstTransaction = model.getTransactions().get(0);
	    checkTransaction(amount, category, firstTransaction);
	
	// Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);
    }


    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
        double amount = 50.0;
        String category = "food";
        Transaction addedTransaction = new Transaction(amount, category);
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains only
	//                the added transaction
        assertEquals(1, model.getTransactions().size());
        Transaction firstTransaction = model.getTransactions().get(0);
        checkTransaction(amount, category, firstTransaction);

        assertEquals(amount, getTotalCost(), 0.01);
	
	// Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testAddTransactionWithValidAmountAndCategory() {
        // New Test Case 1: Add Transaction
        assertEquals(0, model.getTransactions().size());
        double amount = 50.0;
        String category = "food";

        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(amount, category));

        // Post-condition: Verify that the transaction is added to the table
        assertEquals(1, model.getTransactions().size());

        // Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);
        Transaction t = model.getTransactions().get(0);
        checkTransaction(amount, category, t);

        JTable vList = view.getTransactionsTable();

        // Checking if the view is being updated with the transaction
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        // Create a data table for result validation
        Object[][] expectedData = {
                {1, 50.0, "food", formattedDateTime},
                {"Total", null, null, 50.0}
        };

        // Assert values in the transactions table
        for (int row = 0; row < vList.getRowCount(); row++) {
            for (int col = 0; col < vList.getColumnCount(); col++) {
                assertEquals(expectedData[row][col], vList.getValueAt(row, col));
            }
        }


        

    }

    @Test
    public void testInvalidInputHandling() {
        // New Test Case 2: Invalid Input Handling
        // Attempt to add a transaction with an invalid amount or category
        assertEquals(0, model.getTransactions().size());
        double Amount = 10.0;
        String invalidCategory = "Fees";
        // Perform the action: Attempt to add a transaction with invalid input
        

        assertFalse(controller.addTransaction(Amount, invalidCategory));
        // Post-condition: Verify error messages are displayed and transactions remain unchanged
        JTable TransactionTable = view.getTransactionsTable();
        assertEquals(0, TransactionTable.getRowCount());
        assertEquals(0, model.getTransactions().size());
        try{
            Transaction transaction = new Transaction(Amount, invalidCategory);
        }
        catch (IllegalArgumentException exception){
            assertNotNull(exception.getMessage());
            assertEquals("The category is not valid.", exception.getMessage());
        }
        assertEquals(0.0, getTotalCost(), 0.01);



}

    private int findRowIndexInModel(Transaction transaction, DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            // Assuming that the amount is in the second column (index 1)
            Double amount = (Double) model.getValueAt(i, 1);

            // Adjust this condition based on your equality criteria
            if (amount.equals(transaction.getAmount())) {
                return i;
            }
        }

        return -1; // Return -1 if the transaction is not found in the model
    }
    @Test
    public void testFilterByAmount() {
        // New Test Case: Filter by Amount
        // Steps: Add multiple transactions with different amounts, apply amount filter
        // Expected Output: Only transactions matching the amount are returned (and will be highlighted)
        
        // Add multiple transactions with different amounts
        assertEquals(0, model.getTransactions().size());
        controller.addTransaction(30.0, "food");
        controller.addTransaction(40.0, "travel");
        controller.addTransaction(50.0, "food");
        controller.addTransaction(60.0, "food");
        
        // Apply amount filter
        double filterAmount = 50.0;
        controller.setFilter(new AmountFilter(filterAmount));
        controller.applyFilter();
        
        // Get filtered transactions after applying the amount filter
        List<Transaction> filteredByAmount = controller.getSelectedTransactions();
        
        // Validate the expected output - transactions matching the amount are returned
        assertEquals(1, filteredByAmount.size()); // Expected: 1 transaction with amount 50.0 
        for (Transaction transaction : filteredByAmount) {
            assertEquals(50.0, transaction.getAmount(), 0.01);
        }
        for (Transaction transaction : filteredByAmount) {
            int rowIndex = findRowIndexInModel(transaction, view.getTableModel());
            Color expectedColor = new Color(173, 255, 168); // Light green

            // Assuming that the amount is in the second column (index 1)
            Color actualColor = getCellBackgroundColor(view.getTransactionsTable(), rowIndex, 1);

            assertEquals(expectedColor, actualColor);
        }
    }
    private int findRowIndexInModelByCategory(Transaction transaction, DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            // Assuming that the category is in the third column (index 2)
            String category = (String) model.getValueAt(i, 2);

            // Adjust this condition based on your equality criteria
            if (category.equals(transaction.getCategory())) {
                return i;
            }
        }

        return -1; // Return -1 if the transaction is not found in the model
    }

    @Test
    public void testFilterByCategory() {
        // New Test Case: Filter by Category
        // Steps: Add multiple transactions with different categories, apply category filter
        // Expected Output: Only transactions matching the category are returned (and will be highlighted)
        
        // Add multiple transactions with different categories
        assertEquals(0, model.getTransactions().size());
        controller.addTransaction(50.0, "food");
        controller.addTransaction(60.0, "travel");
        controller.addTransaction(100.0, "entertainment");
        controller.addTransaction(70.0, "food");
        controller.addTransaction(80.0, "food");
        
        // Apply category filter
        String filterCategory = "food";
        controller.setFilter(new CategoryFilter(filterCategory));
        controller.applyFilter();
        
        // Get filtered transactions after applying the category filter
        List<Transaction> filteredByCategory = controller.getSelectedTransactions();
        
        // Validate the expected output - transactions matching the category are returned
        assertEquals(3, filteredByCategory.size()); // Expected: 3 transactions with category "food"
        for (Transaction transaction : filteredByCategory) {
            assertEquals("food", transaction.getCategory());
        }
        for (Transaction transaction : filteredByCategory) {
            int rowIndex = findRowIndexInModelByCategory(transaction, view.getTableModel());
            Color expectedColor = new Color(173, 255, 168); // Light green

            // Assuming that the amount is in the second column (index 1)
            Color actualColor = getCellBackgroundColor(view.getTransactionsTable(), rowIndex, 1);

            assertEquals(expectedColor, actualColor);
        }
    }
    
}
