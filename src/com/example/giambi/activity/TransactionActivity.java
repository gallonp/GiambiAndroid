package com.example.giambi.activity;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.presenter.TransactionPresenter;
import com.example.giambi.view.TransactionView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TransactionActivity extends Activity implements
		TransactionView {

	private MyAdapter myAdapter;

	private List<Transaction> transactions = new ArrayList<Transaction>();
	// All transactions must belong to a unique accountNumber of a certain user
	private Long accountNumber = 0L;
	
	private OnItemClickListener onItemClickListener;

	private TransactionPresenter transactionPresenter;
	
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_page);		
		// Create a progess bar to display while list is loaded
//		ProgressBar progressBar = new ProgressBar(this);
//		progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT));
//		progressBar.setIndeterminate(true);
		
//		getListView().setEmptyView(progressBar);
//		ViewGroup root = (ViewGroup) findViewById(R.id.transactionLayout);
//		root.addView(progressBar);
		this.setAccountNumber();
		this.actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		this.myAdapter = new MyAdapter(this);
		this.getListView().setAdapter(this.myAdapter);
		Log.v("onCreateTransationAct","Before Set presenter");
		this.transactionPresenter = new TransactionPresenter(this, this.accountNumber);

	}
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu_options, menu);
        MenuItem[] menuItems = new MenuItem[4];
        menuItems[0] = (MenuItem) menu.findItem(R.id.refresh);
        menuItems[1] = (MenuItem) menu.findItem(R.id.search);
        menuItems[2] = (MenuItem) menu.findItem(R.id.create_new_item);
        menuItems[3] = (MenuItem) menu.findItem(R.id.logout);
        for (MenuItem item : menuItems) {
           item.setOnMenuItemClickListener(menuClickListener);
        }
        return true;
    }

    public OnMenuItemClickListener menuClickListener = new OnMenuItemClickListener(){

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			String itemTitle = item.getTitle().toString();
			Log.v("menu title",(String) item.getTitle()+","+item.getGroupId() );
            if (itemTitle.equals("Refresh")) {
                Log.i("MenuItem", "1");
            } else if (itemTitle.equals("Search")) {
                Log.i("MenuItem", "2");
            } else if (itemTitle.equals("New..")) {
                Log.i("MenuItem", "3");
            } else if (itemTitle.equals("Log Out")) {
                Log.i("MenuItem", "4");
            } else {
                Log.i("MenuItem", "Unknown");
            }
			return false;
		}
    	
    };
	
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
		Transaction one = this.transactions.get(0);
		Log.v("setTransactions", one.name+"," + one.amount+ "," + one.username);
		this.myAdapter.notifyDataSetChanged();
	}

	@Override
	public void updateTransactions() {
		this.myAdapter.notifyDataSetChanged();
	}
	
	

	public class MyAdapter extends BaseAdapter {

		private final Context context;
//		private final List<Transaction> transactions;

		public MyAdapter(Context context) {
			super();
			this.context = context;
			
			Log.v("MyAdapter constructor","instantiation completed");
			Log.v("transactions size",Integer.toString(transactions.size()));
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
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(context);
			Log.v("adapter","inflate view");
			View rowView = inflater.inflate(R.layout.transaction_row, parent,
					false);
			TextView transactionName = (TextView) rowView
					.findViewById(R.id.transactionName);
			TextView category = (TextView) rowView.findViewById(R.id.category);
			TextView amount = (TextView) rowView.findViewById(R.id.amount);
			
			Transaction transaction = transactions.get(position);
			Log.v("setTransactionsView", transaction.name+"," + transaction.amount+ "," + transaction.username);

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
