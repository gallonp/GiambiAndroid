package com.example.giambi.view;

import android.widget.AdapterView.OnItemClickListener;
import com.example.giambi.model.Transaction;

import java.util.List;

public interface TransactionView {

    void setTransactions(List<Transaction> transactions);

    void updateTransactions();

    void addOnItemClickListener(OnItemClickListener listener);

    void showReportDialog();

    String getUsernameFromPreference();

    void showTransactionDetail(Transaction transaction);

}
