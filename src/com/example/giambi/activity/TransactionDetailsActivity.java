package com.example.giambi.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.giambi.MySelectionAdapter;
import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.presenter.TransactionDetailsPresenter;
import com.example.giambi.util.Util;
import com.example.giambi.view.TransactionDetailsView;
import com.example.giambi.view.TransactionView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
@SuppressWarnings("unused")
public class TransactionDetailsActivity extends Activity implements TransactionDetailsView {

	
	private Transaction transaction = null;
	private List<String> categories = new ArrayList<String>();
	private List<String> merchants = new ArrayList<String>();
	
	
	private String username = "";
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
	
	private ListView categoryListView;
	private ListView merchantListView;

	private TransactionDetailsPresenter transactionDialogPresenter;
	
	public TransactionDetailsActivity() {
		
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.transaction_dialog);
		

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
		merchantListView = (ListView) this.findViewById(R.id.merchantListView);
		categoryListView = (ListView) this.findViewById(R.id.categoryListView);
		
		merchantListView.setAdapter(new MySelectionAdapter<String>(this, R.layout.select_item_row, this.merchants));
		categoryListView.setAdapter(new MySelectionAdapter<String>(this, R.layout.select_item_row, this.categories));

		populateFields();
		transactionDialogPresenter = new TransactionDetailsPresenter(this);

	}
	
	@Override
	public void setMerchants(List<String> merchants){
		this.merchants = merchants;
	}
	
	@Override
	public void setCategories(List<String> categories){
		this.categories = categories;
	}
	
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
		Log.v("got Date from UI:", this.date.toString());
		Date date = Util.stringToDate(this.date);
		if (date == null) {
			date = Calendar.getInstance().getTime();
		}

		if (this.keyId != null && !this.keyId.equals("")) {
			newTransaction.id = Long.parseLong(this.keyId);
		}
		newTransaction.addExtraInfo(category, date, merchant, accountNumber);

		// populate the fields of newTransaction with the data in the dialog
		// newTransaction
		return newTransaction;
	}

	@Override
	public void AddOnClickListener(OnClickListener clickerListener) {
		this.addAndSaveButton.setOnClickListener(clickerListener);
	}

    public String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences(
                  "com.example.app", Context.MODE_PRIVATE);
        String username = prefs.getString("USERNAME_GIAMBI", null);
        return username;
    }

}
