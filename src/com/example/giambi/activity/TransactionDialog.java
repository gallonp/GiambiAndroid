//package com.example.giambi.activity;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import com.example.giambi.R;
//import com.example.giambi.model.Transaction;
//import com.example.giambi.presenter.TransactionDialogPresenter;
//import com.example.giambi.util.Util;
//import com.example.giambi.view.AccountView;
//import com.example.giambi.view.TransactionDialogView;
//import com.example.giambi.view.TransactionView;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.database.DataSetObserver;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ExpandableListAdapter;
//import android.widget.ExpandableListView;
//import android.widget.ListView;
//import android.widget.SearchView;
//
//public class TransactionDialog extends DialogFragment  {
//
//	private Transaction transaction = null;
//	private String username = "";
//	private String addOrEdit = "";
//
//	private String accountNumber = "";
//	private String transactionName = "";
//	private String amount = "";
//	private String category = "";
//	private String merchant = "";
//	private String date = "";
//	private String keyId = "";
//
//	private EditText transactionNameField;
//	private EditText amountField;
//	private EditText dateField;
//	private EditText categoryField;
//	private EditText accountNumberField;
//	private EditText merchantField;
//	private Button addAndSaveButton;
//
//	private TransactionDialogPresenter transactionDialogPresenter;
//
//	public TransactionDialog() {
//
//	}
//
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		TransactionActivity v = (TransactionActivity) this.getActivity();
//		LayoutInflater inflater = LayoutInflater.from(v);
//		final View view = inflater.inflate(R.layout.transaction_dialog, null);
//
//		transactionNameField = (EditText) view
//				.findViewById(R.id.transactionName);
//		amountField = (EditText) view.findViewById(R.id.transactionAmount);
//		categoryField = (EditText) view
//				.findViewById(R.id.transactionCategoryEditTextView);
//		accountNumberField = (EditText) view
//				.findViewById(R.id.AccountNumberTextView);
//		merchantField = (EditText) view.findViewById(R.id.merchantEditTextView);
//		dateField = (EditText) view.findViewById(R.id.transactionDate);
//		addAndSaveButton = (Button) view
//				.findViewById(R.id.addTransactionButton);
//
//		populateFields(view);
//
//		transactionDialogPresenter = new TransactionDialogPresenter(this);
//
//		return new AlertDialog.Builder(this.getActivity()).setTitle(addOrEdit)
//				.setView(view).create();
//	}
//
//	// only populate the fields when there is bundle from previous activitys
//	private void populateFields(View addView) {
//		Bundle b = this.getArguments();
//		addOrEdit = b.getString("AddOrEdit");
//		if (!addOrEdit.contains("Add")) {
//			keyId = b.getString("KeyId");
//			Log.v("KeyId:", keyId + "");
//			accountNumber = b.getString("AccountNumber");
//			transactionName = b.getString("TransactionName");
//			amount = b.getString("Amount");
//			category = b.getString("Category");
//			merchant = b.getString("Merchant");
//			date = b.getString("Date");
//			this.amountField.setText(amount);
//			this.transactionNameField.setText(transactionName);
//			this.dateField.setText(date);
//			this.addAndSaveButton.setText("Save");
//			// Set accountNumber to its original one and forbid editing
//			this.accountNumberField.setText(accountNumber);
//			this.accountNumberField.setEnabled(false);
//			this.categoryField.setText(this.category);
//			this.merchantField.setText(this.merchant);
//		}
//		accountNumber = b.getString("AccountNumber");
//		this.accountNumberField.setText(accountNumber);
//		this.accountNumberField.setEnabled(false);
//
//		this.accountNumberField.setText(accountNumber);
//
//	}
//
//	private void syncUIData() {
//		accountNumber = this.accountNumberField.getText().toString();
//		transactionName = this.transactionNameField.getText().toString();
//		amount = this.amountField.getText().toString();
//		category = this.categoryField.getText().toString();
//		merchant = this.merchantField.getText().toString();
//		date = this.dateField.getText().toString();
//		// username and keyId don't need to sync
//	}
//
//	@SuppressWarnings("deprecation")
//	@Override
//	public Transaction getCurrentTransactionData() {
//		syncUIData();
//
//		String username = ((TransactionActivity) this.getActivity())
//				.getUsernameFromPreference();
//		Log.v("username passed to transaction", username);
//		Transaction newTransaction = new Transaction(this.transactionName,
//				Double.parseDouble(this.amount), username);
//		// Need to check transaction name, amount are not null
//		Log.v("got Date from UI:", this.date.toString());
//		Date date = Util.stringToDate(this.date);
//		if (date == null) {
//			date = Calendar.getInstance().getTime();
//		}
//
//		if (this.keyId != null && !this.keyId.equals("")) {
//			newTransaction.id = Long.parseLong(this.keyId);
//		}
//		newTransaction.addExtraInfo(category, date, merchant, accountNumber);
//
//		// populate the fields of newTransaction with the data in the dialog
//		// newTransaction
//		return newTransaction;
//	}
//
//	@Override
//	public void AddOnClickListener(OnClickListener clickerListener) {
//		this.addAndSaveButton.setOnClickListener(clickerListener);
//	}
//
//	@Override
//	public TransactionView getTransactionActivity() {
//		return (TransactionView) this.getActivity();
//	}
//
//	// public <T> void setListAdapter(ListView listView,List<T> data){
//	// listView.setAdapter(new ArrayAdapter<T>(this.getActivity(),
//	// R.layout.simple_string_list, data){
//	// });
//	// }
//}
