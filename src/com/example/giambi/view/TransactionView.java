package com.example.giambi.view;

import java.util.List;

import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.model.Transaction;

public interface TransactionView {
    
    void setTransactions(List<Transaction> transactions);

    void updateTransactions();

    void addOnItemClickListener(OnItemClickListener listener);

    String getAccountNum();
    
    void showReportDialog();
    /**
     * gets username.
     * @return the username
     */
    String getUsernameFromPreference();
    /**
     * This shows transaction detail
     * @param transaction this is it
     */
    void showTransactionDetail(Transaction transaction);

    void setDate(String date1, String date2);
}
