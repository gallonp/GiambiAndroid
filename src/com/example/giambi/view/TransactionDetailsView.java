package com.example.giambi.view;

import java.util.List;

import com.example.giambi.activity.TransactionActivity;
import com.example.giambi.model.Transaction;

import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public interface TransactionDetailsView {
	
	void AddOnClickListener(OnClickListener clickerListener);
	
	Transaction getCurrentTransactionData();
	
	String getUsernameFromPreference();

	void finish();
	
	void setCategories(String categories);
	
	void addOnItemClickListener(OnItemClickListener listener, ListView list);
	
}
