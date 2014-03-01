package com.example.giambi.activity;

import java.util.List;

import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.view.TransactionView;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TransactionActivity extends ListActivity implements TransactionView {

	private MyAdapter myAdapter;
	
	private List<Transaction> transactions = null;
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.transaction_page);
		 //Create a progess bar to display while list is loaded
		 ProgressBar progressBar = new ProgressBar(this);
		 progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		 progressBar.setIndeterminate(true);
		 getListView().setEmptyView(progressBar);
		 ViewGroup root = (ViewGroup) findViewById(R.id.transactionLayout);
		 root.addView(progressBar);
		 
		 
	}
	
	public ListView getListView(){
		return (ListView) findViewById(R.id.transactionListView);
	}

	@Override
	public void setTransactions(List<Transaction> transactions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTransactions() {
		// TODO Auto-generated method stub
		
	}

	
	public class MyAdapter extends BaseAdapter{
		
		private final Context context;
		private final List<Transaction> transactions;
		public MyAdapter(Context context, List<Transaction> trans){
			super();
			this.context = context;
			this.transactions = trans;
		}
		
		@Override
		public int getCount() {
			return transactions.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
//			String item = getItem(position);
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.transaction_row,parent, false);
			TextView transactionName = (TextView) rowView.findViewById(R.id.transactionName);
			TextView category = (TextView) rowView.findViewById(R.id.category);
			TextView amount = (TextView) rowView.findViewById(R.id.amount);
			
			Transaction transaction = transactions.get(position);
			//set field from transaction
			transactionName.setText(transaction.toString());
			
			return null;
		}
		
	}
}
