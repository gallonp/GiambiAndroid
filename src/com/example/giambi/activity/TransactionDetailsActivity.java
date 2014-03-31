package com.example.giambi.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.giambi.ListSelectionDialog;
import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.presenter.TransactionDetailsPresenter;
import com.example.giambi.util.Util;
import com.example.giambi.view.TransactionDetailsView;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangjialiang
 * Render transaction detail page to either create or update a transaction
 */
public class TransactionDetailsActivity extends Activity implements
        TransactionDetailsView {

    private String addOrEdit = "";

    private String accountNumber = "";
    private String transactionName = "";
    private String amount = "";
    private String category = "";
    private String merchant = "";
    private String date = "";
    private String keyId = "";

    private EditText transactionNameField;
    private EditText amountField;
    private EditText dateField;
    private EditText categoryField;
    private EditText accountNumberField;
    private EditText merchantField;
    private Button addAndSaveButton;

    private ActionBar actionBar;

    private TransactionDetailsPresenter transactionDetailsPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.transaction_details);
        this.actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        transactionNameField = (EditText) this
                .findViewById(R.id.transactionName);
        amountField = (EditText) this.findViewById(R.id.transactionAmount);
        categoryField = (EditText) this
                .findViewById(R.id.transactionCategoryEditTextView);
        accountNumberField = (EditText) this
                .findViewById(R.id.AccountNumberTextView);
        merchantField = (EditText) this.findViewById(R.id.merchantEditTextView);
        dateField = (EditText) this.findViewById(R.id.transactionDate);
        addAndSaveButton = (Button) this
                .findViewById(R.id.addTransactionButton);
        populateFields();
        transactionDetailsPresenter = new TransactionDetailsPresenter(this);
        this.categoryField.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                ListSelectionDialog dialog = new ListSelectionDialog();
                dialog.show(ft, "listDialog");
            }

        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("TransactionDetailActiviy", "OnStop");
        Intent intent = new Intent();
        intent.putExtra("AccountNumber", this.accountNumber);
        this.setResult(RESULT_OK, intent);
    }

    /**
     * populate the fields with transaction details.
     */
    private void populateFields() {
        Bundle b = this.getIntent().getExtras();
        addOrEdit = b.getString("AddOrEdit");
        if (!addOrEdit.contains("Add")) {
            keyId = b.getString("KeyId");
            Log.v("KeyId:", keyId + "");
            accountNumber = b.getString("AccountNumber");
            transactionName = b.getString("TransactionName");
            amount = b.getString("Amount");
            category = b.getString("Category");
            merchant = b.getString("Merchant");
            date = b.getString("Date");
            this.amountField.setText(amount);
            this.transactionNameField.setText(transactionName);
            this.dateField.setText(date);
            this.addAndSaveButton.setText("Save");
            // Set accountNumber to its original one and forbid editing
            this.accountNumberField.setText(accountNumber);
            this.accountNumberField.setEnabled(false);
            this.categoryField.setText(this.category);
            this.merchantField.setText(this.merchant);
        }
        accountNumber = b.getString("AccountNumber");
        this.accountNumberField.setText(accountNumber);
        this.accountNumberField.setEnabled(false);

        this.accountNumberField.setText(accountNumber);

    }

    /**
     * syncUIData with class varibales.
     */
    private void syncUIData() {
        accountNumber = this.accountNumberField.getText().toString();
        transactionName = this.transactionNameField.getText().toString();
        amount = this.amountField.getText().toString();
        category = this.categoryField.getText().toString();
        merchant = this.merchantField.getText().toString();
        date = this.dateField.getText().toString();
        // username and keyId don't need to sync
    }

    @SuppressWarnings("deprecation")
    @Override
    public Transaction getCurrentTransactionData() {
        syncUIData();

        String username = this.getUsernameFromPreference();
        Log.v("username passed to transaction", username);
        Transaction newTransaction = new Transaction(this.transactionName,
                Double.parseDouble(this.amount), username);
        // Need to check transaction name, amount are not null
        Log.v("got Date from UI:", this.date);
        Date tranDate = Util.stringToDate(this.date);
        if (tranDate == null) {
            tranDate = Calendar.getInstance().getTime();
        }

        if (this.keyId != null && !this.keyId.equals("")) {
            newTransaction.id = Long.parseLong(this.keyId);
        }
        newTransaction.addExtraInfo(category, tranDate, merchant, accountNumber);

        // populate the fields of newTransaction with the data in the dialog
        // newTransaction
        return newTransaction;
    }

    @Override
    public void addOnClickListener(OnClickListener clickerListener) {
        this.addAndSaveButton.setOnClickListener(clickerListener);
    }


    @Override
    public String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences("com.example.app",
                Context.MODE_PRIVATE);
        return prefs.getString("USERNAME_GIAMBI", null);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * transaction menu.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, TransactionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("AccountNumber", this.accountNumber);
                i.putExtras(bundle);
                startActivity(i);
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void addOnItemClickListener(OnItemClickListener listener,
            ListView list) {
        list.setOnItemClickListener(listener);
    }

    @Override
    public void setCategories(String category) {
        this.categoryField.setText(category);
    }

}
