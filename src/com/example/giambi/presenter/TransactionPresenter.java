package com.example.giambi.presenter;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.model.Transaction;
import com.example.giambi.view.TransactionView;

public class TransactionPresenter {

	private TransactionView v;
	
	private List<Transaction> transactions;
	
	private long accountNumber=0;
	
	private String username;
	
	public TransactionPresenter(TransactionView v, long accountNumber) {
		this.v = v;
		//getData from transaction
		username = v.getUsernameFromPreference();
		this.accountNumber = accountNumber;
		this.transactions = Transaction.getAccountTransactions(username,accountNumber);
		v.setTransactions(transactions);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			v.showTransactionDetail(transactions.get(position));
		}
		
	};

}
