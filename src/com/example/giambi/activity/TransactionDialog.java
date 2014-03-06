package com.example.giambi.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.giambi.R;
import com.example.giambi.model.Transaction;
import com.example.giambi.view.AccountView;
import com.example.giambi.view.TransactionView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;

public class TransactionDialog extends DialogFragment {

	private Transaction transaction = null;
	private String username = "";
	private String addOrEdit = "";
	
	private String accountNumber = "";
	private String transactionName = "";
	private String amount = "";
	private String category = "";
	private String merchant = "";
	private String date = "";
	
	private EditText transactionNameField;
	private EditText amountField;
	private EditText dateField;
	private ListView categoryField;
	private EditText transactionAccountNumberField;
	private SearchView merchantField;
	private Button addAndSaveButton;
	
	public TransactionDialog() {

	}

	@Override
	public Dialog onCreateDialog(
			Bundle savedInstanceState) {
		TransactionActivity v = (TransactionActivity) this.getActivity();
		LayoutInflater inflater = LayoutInflater.from(v);
		final View view = inflater
				.inflate(R.layout.transaction_dialog, null);
		
		transactionNameField = (EditText) view
				.findViewById(R.id.transactionName);
		amountField = (EditText) view
				.findViewById(R.id.transactionAmount);
		categoryField = (ListView) view
				.findViewById(R.id.transactionCategoryExpandableListView);
		transactionAccountNumberField = (EditText) view
				.findViewById(R.id.AccountNumberTextView);
		merchantField = (SearchView) view
				.findViewById(R.id.merchantExpandableListView);
		dateField = (EditText) view.findViewById(R.id.transactionDate);
		addAndSaveButton = (Button) view.findViewById(R.id.addTransactionButton);

		populateFields(view);
		
		return new AlertDialog.Builder(this.getActivity()).setTitle(addOrEdit)
				.setView(view).create();
	}
	
	public <T> void  setListAdapter(ListView listView,List<T> data){
		listView.setAdapter(new ArrayAdapter<T>(this.getActivity(), R.layout.simple_string_list, data){
		});
	}
	
	
	//only populate the fields when there is bundle from previous activitys
	private void populateFields(View addView) {
		Bundle b = this.getArguments();
		addOrEdit = b.getString("AddOrEdit");
		if (!addOrEdit.contains("Add")){
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
		this.transactionAccountNumberField.setText("1245263");
		this.transactionAccountNumberField.setEnabled(false);
		}
	}
	
	private Transaction getCurrentTransactionData(){

		Transaction newTransaction = new Transaction(this.transactionName, Double.parseDouble(this.amount), null);
		//populate the fields of newTransaction with the data in the dialog
		return null;
	}
		
		
	
}
