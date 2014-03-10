package com.example.giambi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

public class MySelectionAdapter<T> extends ArrayAdapter<T> {
	
	List<T>  data = new ArrayList<T>();
	private Context context;
	
	public MySelectionAdapter(Context context, int resource, List<T> data) {
		super(context, resource, data);
		this.data = data;
		
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public T getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		Log.v("getItemId", Integer.toString(position));
		T obj = data.get(position);
		return obj.hashCode();
	}
	
	static class ViewHolder{
		public RadioButton radionButton;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			rowView = inflater.inflate(R.layout.transaction_row, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.radionButton = (RadioButton) rowView.findViewById(R.id.radioButton);
//			viewHolder.transactionName = (TextView) rowView
//					.findViewById(R.id.transactionName);
//			viewHolder.category = (TextView) rowView
//					.findViewById(R.id.category);
//			viewHolder.amount = (TextView) rowView.findViewById(R.id.amount);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
//		Transaction transaction = null;
		T obj = null;
		try {
			obj = data.get(position);
//			transaction = transactionsInList.get(position);
		} catch (IndexOutOfBoundsException e) {
			return rowView;
		}
//		Log.v("setTransactionsView", transaction.transactionName + ","
//				+ transaction.amount + "," + transaction.username);
		holder.radionButton.setText(obj.toString());
		// set field from transaction
//		holder.transactionName.setText(transaction.transactionName);
//		holder.category.setText(transaction.category);
//		holder.amount.setText(Currency.getInstance(Locale.US).getSymbol()
//				+ Double.toString(transaction.amount));

		return rowView;
	}

}
