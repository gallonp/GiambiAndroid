package com.example.giambi.view;

import java.util.List;

import com.example.giambi.model.Transaction;

public interface TransactionView {

	void setTransactions(List<Transaction> transactions);
	
	void updateTransactions();
	
}
