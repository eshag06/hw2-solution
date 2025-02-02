package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  /** 
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class 
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
  }

  public void setFilter(TransactionFilter filter) {
    // Sets the Strategy class being used in the applyFilter method.
    this.filter = filter;
  }

  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }

  public void applyFilter() {
    //null check for filter
    if(filter!=null){
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      List<Transaction> filteredTransactions = filter.filter(transactions);
      List<Integer> rowIndexes = new ArrayList<>();
      for (Transaction t : filteredTransactions) {
        int rowIndex = transactions.indexOf(t);
        if (rowIndex != -1) {
          rowIndexes.add(rowIndex);
        }
      }
      view.highlightRows(rowIndexes);
    }
    else{
      JOptionPane.showMessageDialog(view, "No filter applied");
      view.toFront();}

  }

  public List<Transaction> getSelectedTransactions() {
    // Check if a filter is applied
    if (filter != null) {
        List<Transaction> transactions = model.getTransactions();
        return filter.filter(transactions);
    } else {
        return model.getTransactions(); // Return all transactions if no filter is applied
    }
  }

  // Added below functions for undo functionality
  public void applyUndo(int[] rows){
    List<Transaction> transactions = model.getTransactions();

    for(int row : rows){
      model.removeTransaction(transactions.get(row));
    }
    refresh();

    if(filter != null){
      applyFilter(); //When filter is not null, we apply filter on the rows removed.
    }

  }

  public void refreshUndoButton(){
    int[] getRowsFromModel =  model.getRowsSelected();
    if(getRowsFromModel.length > 0){
      view.enableUndoButton();
    }
    else{
      view.disableUndoButton();
    }
  }
  
}