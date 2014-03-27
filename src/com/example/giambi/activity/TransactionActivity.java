package com.example.giambi.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.giambi.MyListAdapter;
import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.presenter.TransactionPresenter;
import com.example.giambi.view.TransactionView;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends Activity implements TransactionView {

    private MyListAdapter myAdapter;

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
        // ProgressBar progressBar = new ProgressBar(this);
        // progressBar.setLayoutParams(new
        // LayoutParams(LayoutParams.WRAP_CONTENT,
        // LayoutParams.WRAP_CONTENT));
        // progressBar.setIndeterminate(true);

        // getListView().setEmptyView(progressBar);
        // ViewGroup root = (ViewGroup) findViewById(R.id.transactionLayout);
        // root.addView(progressBar);
        this.username = this.getUsernameFromPreference();
        if (savedInstanceState == null)
            setAccountNumber(getIntent().getExtras());
        else
            setAccountNumber(savedInstanceState);
        // this.setAccountNumber();
        this.actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.myAdapter = new MyListAdapter(this, transactions);
        this.transactionList = (ListView) findViewById(R.id.transactionListView);
        this.transactionList.setAdapter(this.myAdapter);

        this.transactionPresenter = new TransactionPresenter(this,
                this.accountNumber);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("AccountNumber", this.accountNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transactionactivity_menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.trans_refresh:
                updateTransactions();
                return true;

            case R.id.trans_create_new_item:
                Transaction newTransaction = new Transaction("", 0, username);
                newTransaction.accountNumber = accountNumber;
                Log.v("accountNumber passed to new", accountNumber);
                showTransactionDetail(newTransaction);
                Log.i("MenuItem", "3");
                return true;

            case R.id.trans_report:
                startReport("Spending");
            case R.id.trans_logout:
                return true;
            case R.id.trans_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startReport(String type) {
        Intent i = new Intent(this, ReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("AccountNumber", this.accountNumber);
        bundle.putString("ReportType", type);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void setAccountNumber(Bundle b) {
        // Bundle b = getIntent().getExtras();
        String accNumber = "";
        if (b != null) {
            try {
                long value = Long.parseLong(b.getString("AccountNumber"));
                accNumber = Long.toString(value);
            } catch (NumberFormatException e) {
                Log.v("TransactionActivity", "Error in parsing AccountNumber");
            }
            if (accNumber.isEmpty()) {
                // some ways to handle no account number
                this.finish();
            } else {
                this.accountNumber = accNumber;
            }
        }
    }

    /**
     * Get username from preference.
     *
     * @return current username
     */

    public String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences("com.example.app",
                Context.MODE_PRIVATE);
        String username = prefs.getString("USERNAME_GIAMBI", null);
        return username;
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        this.myAdapter.transactionsInList = transactions;
        this.myAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateTransactions() {
        this.transactionPresenter.updateTransactions();
    }

    @Override
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.transactionList.setOnItemClickListener(listener);
    }

    @Override
    public void showTransactionDetail(Transaction transaction) {
        Log.i("DialogFragment", "show new dialog fragment");
        Intent i = new Intent(this, TransactionDetailsActivity.class);
        // FragmentTransaction ft = getFragmentManager().beginTransaction();
        // dialog = new TransactionDialog();
        if (!transaction.transactionName.isEmpty()) {
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
            i.putExtras(b);
            startActivityForResult(i, 1);
        } else {
            Bundle b = new Bundle();
            b.putString("AddOrEdit", "Add Transaction");
            b.putString("AccountNumber", transaction.accountNumber);
            i.putExtras(b);
            startActivityForResult(i, 2);
            // dialog.setArguments(b);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.updateTransactions();
        this.accountNumber = data.getExtras().getString("AccountNumber");
    }

}
