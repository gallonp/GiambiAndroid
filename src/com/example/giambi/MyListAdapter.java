package com.example.giambi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.giambi.model.Transaction;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class MyListAdapter extends ArrayAdapter<Transaction> {

	private final Context context;
	public List<Transaction> transactionsInList;

	public MyListAdapter(Context context, List<Transaction> transactions) {
		super(context, R.layout.transaction_row, transactions);
		this.context = context;
		this.transactionsInList = transactions;
		Log.v("MyAdapter constructor", "instantiation completed");
		Log.v("transactions size", Integer.toString(transactions.size()));
	}

	@Override
	public int getCount() {
		return transactionsInList.size();
	}

	@Override
	public Transaction getItem(int position) {
		return transactionsInList.get(position);
	}

	@Override
	public long getItemId(int position) {
		Log.v("getItemId", Integer.toString(position));
		Transaction transaction = transactionsInList.get(position);
		return transaction.getId();
	}

	static class ViewHolder {
		public TextView transactionName;
		public TextView category;
		public TextView amount;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			rowView = inflater.inflate(R.layout.transaction_row, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.transactionName = (TextView) rowView
					.findViewById(R.id.transactionName);
			viewHolder.category = (TextView) rowView
					.findViewById(R.id.category);
			viewHolder.amount = (TextView) rowView.findViewById(R.id.amount);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		Transaction transaction = null;
		try {
			transaction = transactionsInList.get(position);
		} catch (IndexOutOfBoundsException e) {
			return rowView;
		}
		Log.v("setTransactionsView", transaction.transactionName + ","
				+ transaction.amount + "," + transaction.username);

		// set field from transaction
		holder.transactionName.setText(transaction.transactionName);
		holder.category.setText(transaction.category);
		holder.amount.setText(Currency.getInstance(Locale.US).getSymbol()
				+ Double.toString(transaction.amount));

		return rowView;
	}

}