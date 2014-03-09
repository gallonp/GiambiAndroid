package com.example.giambi.activity;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.example.giambi.MyAdapter;
import com.example.giambi.NewBankAccountDialogFragment;
import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.presenter.TransactionPresenter;
import com.example.giambi.view.TransactionView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TransactionActivity extends Activity implements
		TransactionView {

	private MyAdapter myAdapter;

	private List<Transaction> transactions = new ArrayList<Transaction>();
	// All transactions must belong to a unique accountNumber of a certain user
	private String accountNumber = "";
	
	private String username = "";
	
	private TransactionPresenter transactionPresenter;
	
	private ActionBar actionBar;
	
	private DialogFragment dialog;
	
	private ListView transactionList;
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
		this.username = this.getUsernameFromPreference();
		this.setAccountNumber();
		this.actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		this.myAdapter = new MyAdapter(this, transactions);
		this.transactionList = (ListView) findViewById(R.id.transactionListView);
		this.transactionList.setAdapter(this.myAdapter);
		
		Bundle b = this.getIntent().getExtras();
		this.accountNumber = (String) b.get("AccountNumber");
		
		this.transactionPresenter = new TransactionPresenter(this, this.accountNumber);

		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.refresh:
            	updateTransactions();
                return true;
                
            case R.id.create_new_item:
            	Transaction newTransaction = new Transaction("", 0, username);
            	newTransaction.accountNumber = accountNumber;
            	Log.v("accountNumber passed to new", accountNumber);
            	showTransactionDetail(newTransaction);
            	Log.i("MenuItem", "3");
                return true;
                
            case R.id.logout:
            	return true;
            case R.id.search:
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
	private void setAccountNumber(){
		Bundle b = getIntent().getExtras();
		String accNumber ="";
		try{
		long value = Long.parseLong(b.getString("AccountNumber"));
		accNumber = Long.toString(value);
		} catch (NumberFormatException e){
			Log.v("TransactionActivity","Error in parsing AccountNumber");
		}
		if (accNumber.isEmpty()){
			//some ways to hanlde no account number
			this.finish();
		} else {
			this.accountNumber = accNumber;
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
    
	@Override
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
		this.myAdapter.transactionsInList = transactions;
//		Transaction one = this.transactions.get(0);
//		Log.v("setTransactions", one.transactionName+"," + one.amount+ "," + one.username);
//		this.myAdapter.notifyDataSetChanged();
	}

	@Override
	public void updateTransactions() {
		this.myAdapter.transactionsInList = this.transactions;
		this.myAdapter.notifyDataSetChanged();
	}
	
	

	

	@Override
	public void addOnItemClickListener(OnItemClickListener listener) {
		this.transactionList.setOnItemClickListener(listener);
	}

	@Override
	public void showTransactionDetail(Transaction transaction) {
        Log.i("DialogFragment", "show new dialog fragment");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog = new TransactionDialog();
        if(!transaction.transactionName.isEmpty()){
            Bundle b = new Bundle();
            b.putString("AddOrEdit", "Transaction Details");
            b.putString("TransactionName", transaction.transactionName);
            b.putString("AccountNumber", transaction.accountNumber);
            b.putString("Category", transaction.category);
            b.putString("Amount", Double.toString(transaction.amount));
            b.putString("Merchant", transaction.merchant);
            b.putString("Date", transaction.createDate.toString());
            b.putString("Username", this.username);
            b.putString("KeyId", Long.toString(transaction.id));
            dialog.setArguments(b);
        } else {
        	Bundle b = new Bundle();
            b.putString("AddOrEdit", "Add Transaction");
            b.putString("AccountNumber", transaction.accountNumber);

            dialog.setArguments(b);
        }
//        b.putString("transactionName", )
//        b.putSerializable(key, value)("Transaction", this.accountNumber);
        dialog.show(ft, "dialog");
	}
}
