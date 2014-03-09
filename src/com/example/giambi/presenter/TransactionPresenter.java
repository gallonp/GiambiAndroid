package com.example.giambi.presenter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.model.Transaction;
import com.example.giambi.view.TransactionView;

public class TransactionPresenter {

	private TransactionView v;
	
	private List<Transaction> transactions;
	
	private String accountNumber="";
	
	private String username;
	
	public TransactionPresenter(TransactionView v, String accountNumber) {
		this.v = v;
		//getData from transaction
		username = v.getUsernameFromPreference();
		Log.v("username from preference", username);
		this.accountNumber = accountNumber;
		
		this.transactions = Transaction.getAccountTransactions(username, this.accountNumber);
		Log.v("transactions to present",transactions.size()+"");
//		if (transactions != null){
//			this.transactions.get(0).createDate = Calendar.getInstance().getTime();
//			this.transactions.get(1).createDate = Calendar.getInstance().getTime();
//			this.transactions.get(2).createDate = Calendar.getInstance().getTime();
		v.setTransactions(transactions);
		v.addOnItemClickListener(itemClickListener);
//		}
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			v.showTransactionDetail(transactions.get(position));
		}
		
	};
	
	public void updateTransactions(){
		this.transactions = Transaction.getAccountTransactions(username, this.accountNumber);
		v.setTransactions(transactions);
	}

}
