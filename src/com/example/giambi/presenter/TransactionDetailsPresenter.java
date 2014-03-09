package com.example.giambi.presenter;

import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.giambi.model.Transaction;
import com.example.giambi.view.TransactionDetailsView;

public class TransactionDetailsPresenter {
	
	
	private TransactionDetailsView v;
	
	public TransactionDetailsPresenter(TransactionDetailsView view) {
		
		this.v = view;
		v.AddOnClickListener(onClickListener);
		
	}

	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			Log.v("Add button Clicked","clicked!!!!");
			Transaction newTransaction = v.getCurrentTransactionData();
			Transaction.persistTransaction(newTransaction);
			Log.v("newTransaction to persist", newTransaction.id+"");
			v.finish();
//			List<Transaction> transactions = Transaction.getAccountTransactions(v.getUsernameFromPreference(),"" );
//			v.getTransactionActivity().setTransactions(transactions);
//			v.getTransactionActivity().updateTransactions();
			
		}
		
	};
}
