package com.example.giambi.view;

import java.util.List;

import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.model.Transaction;

public interface TransactionView {

	void setTransactions(List<Transaction> transactions);
	
	void updateTransactions();
	
	void addOnItemClickListener(OnItemClickListener listener);
	
	String getUsernameFromPreference();
	
	void showTransactionDetail(Transaction transaction);
}
