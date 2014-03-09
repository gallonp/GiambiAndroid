package com.example.giambi.view;

import com.example.giambi.activity.TransactionActivity;
import com.example.giambi.model.Transaction;

import android.view.View.OnClickListener;

public interface TransactionDialogView {
	
	void AddOnClickListener(OnClickListener clickerListener);
	
	Transaction getCurrentTransactionData();
	
	String getUsernameFromPreference();

	void finish();
	
}
