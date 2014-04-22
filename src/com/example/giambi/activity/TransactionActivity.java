package com.example.giambi.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.giambi.DateListener;
import com.example.giambi.DatePickerDialogFragment;
import com.example.giambi.MyListAdapter;
import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.presenter.TransactionPresenter;
import com.example.giambi.view.TransactionView;

/**
 * @author zhangjialiang Render transaction list page
 */
public class TransactionActivity extends NavigationDrawerActivity implements
        TransactionView, DateListener {

    private MyListAdapter myAdapter;

    private List<Transaction> transactions = new ArrayList<Transaction>();
    // All transactions must belong to a unique accountNumber of a certain user
    private String accountNumber = "";
    private String username = "";
    private String startDate = "";
    private String endDate = "";
    private TransactionPresenter transactionPresenter;
    private DialogFragment dialog;
    private ListView transactionList;

    @Override
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.transactionList.setOnItemClickListener(listener);
    }

    @Override
    public String getAccountNum() {
        System.out.println(this.accountNumber);
        return this.accountNumber;
    }

    /**
     * Get username from preference.
     * 
     * @return current username
     */

    public String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences("com.example.app",
                Context.MODE_PRIVATE);
        String usrname = prefs.getString("USERNAME_GIAMBI", null);
        return usrname;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transactionactivity_menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            showReportDialog();
            return true;
        case R.id.trans_logout:
            getAccountNum();
            return true;
        case R.id.trans_search:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("AccountNumber", this.accountNumber);
    }

    @Override
    public void setDate(String date1, String date2) {
        this.startDate = date1;
        this.endDate = date2;
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        this.myAdapter.transactionsInList = transactions;
        this.myAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReportDialog() {
        Log.i("DialogFragment", "show new dialog fragment");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog = new DatePickerDialogFragment(this);
        dialog.setArguments(null);
        dialog.show(ft, "dialog");
    }

    @Override
    public void showTransactionDetail(Transaction transaction) {
        Log.i("DialogFragment", "show new dialog fragment");
        Intent i = new Intent(this, TransactionDetailsActivity.class);
        if (transaction.transactionName.isEmpty()) {
            Bundle b = new Bundle();
            b.putString("AddOrEdit", "Add Transaction");
            b.putString("AccountNumber", transaction.accountNumber);
            i.putExtras(b);
            startActivityForResult(i, 2);
        } else {
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
        }
    }

    /**
     * Start a new report activity.
     * 
     * @param type
     *            report type
     */
    public void startReport(String type) {
        Intent i = new Intent(this, ReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("AccountNumber", this.accountNumber);
        bundle.putString("ReportType", type);
        bundle.putString("StartDate", startDate);
        bundle.putString("EndDate", endDate);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void updateTransactions() {
        this.transactionPresenter.updateTransactions();
    }

    /*
     * (non-Javadoc) should update the transaction list after the return from
     * transaction detail page
     * 
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.updateTransactions();
        // this.accountNumber = data.getExtras().getString("AccountNumber");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.username = this.getUsernameFromPreference();
        if (savedInstanceState == null) {
            setAccountNumber(getIntent().getExtras());
        } else {
            setAccountNumber(savedInstanceState);
        }
        this.myAdapter = new MyListAdapter(this, transactions);
        this.transactionList = (ListView) findViewById(R.id.transactionListView);
        this.transactionList.setAdapter(this.myAdapter);

        this.transactionPresenter = new TransactionPresenter(this,
                this.accountNumber);
    }

    @Override
    protected void setupView() {
        setContentView(R.layout.transaction_page);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.transactionLayout);
        mDrawerList = (ListView) findViewById(R.id.nav_bar_transaction);
    }

    /**
     * Sets account number from bundle.
     * 
     * @param b
     *            savedInstanceBundle
     */
    private void setAccountNumber(Bundle b) {
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
}
