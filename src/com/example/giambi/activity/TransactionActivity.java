package com.example.giambi.activity;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.presenter.TransactionPresenter;
import com.example.giambi.view.TransactionView;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TransactionActivity extends ListActivity implements
		TransactionView {

	private MyAdapter myAdapter;

	private List<Transaction> transactions = null;
	// All transactions must belong to a unique accountNumber of a certain user
	private Long accountNumber = 0L;
	
	private OnItemClickListener onItemClickListener;

	private TransactionPresenter transactionPresenter;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_page);		
		// Create a progess bar to display while list is loaded
		ProgressBar progressBar = new ProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		progressBar.setIndeterminate(true);
		getListView().setEmptyView(progressBar);
		ViewGroup root = (ViewGroup) findViewById(R.id.transactionLayout);
		root.addView(progressBar);
		this.setAccountNumber();
		this.transactionPresenter = new TransactionPresenter(this, this.accountNumber);
	}
	
	
	
	private void setAccountNumber(){
		Bundle b = getIntent().getExtras();
		long value = Long.parseLong(b.getString("AccountNumber"));
		if (value == 0){
			//some ways to hanlde no account number
		} else {
			this.accountNumber = value;
		}
	}

	
    /**
     * Get username from preference.
     * @return current username
     */
    public String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences(
                  "com.example.app", Context.MODE_PRIVATE);
        String username = prefs.getString("USERNAME_GIAMBI", null);
        return username;
    }
    
	public ListView getListView() {
		return (ListView) findViewById(R.id.transactionListView);
	}

	@Override
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
		this.myAdapter.notifyDataSetChanged();
	}

	@Override
	public void updateTransactions() {
		this.myAdapter.notifyDataSetChanged();
	}
	
	

	public class MyAdapter extends BaseAdapter {

		private final Context context;
		private final List<Transaction> transactions;

		public MyAdapter(Context context, List<Transaction> trans) {
			super();
			this.context = context;
			this.transactions = trans;
		}

		@Override
		public int getCount() {
			return transactions.size();
		}

		@Override
		public Object getItem(int position) {
			return transactions.get(position);
		}

		@Override
		public long getItemId(int position) {
			Transaction transaction = transactions.get(position);
			return transaction.id;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.transaction_row, parent,
					false);
			TextView transactionName = (TextView) rowView
					.findViewById(R.id.transactionName);
			TextView category = (TextView) rowView.findViewById(R.id.category);
			TextView amount = (TextView) rowView.findViewById(R.id.amount);

			Transaction transaction = transactions.get(position);
			// set field from transaction
			transactionName.setText(transaction.name);
			category.setText(transaction.category);
			amount.setText(Currency.getInstance(Locale.US).getSymbol()
					+ Double.toString(transaction.amount));

			return rowView;
		}

	}



	@Override
	public void addOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
		this.getListView().setOnItemClickListener(this.onItemClickListener);
	}



	@Override
	public void showTransactionDetail(Transaction transaction) {
		
	}
}
