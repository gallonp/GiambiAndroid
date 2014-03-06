package com.example.giambi.presenter;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.giambi.model.Transaction;
import com.example.giambi.view.TransactionDialogView;

public class TransactionDialogPresenter {
	
	
	private TransactionDialogView v;
	
	public TransactionDialogPresenter(TransactionDialogView view) {
		
		this.v = view;
		v.AddOnClickListener(onClickListener);
		
	}

	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			Log.v("Add button Clicked","clicked!!!!");
			Transaction newTransaction = v.getCurrentTransactionData();
			Transaction.persistTransaction(newTransaction);
		}
		
	};
}
