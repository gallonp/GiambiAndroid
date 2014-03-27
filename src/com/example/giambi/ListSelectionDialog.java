package com.example.giambi;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.example.giambi.MyListAdapter.ViewHolder;
import com.example.giambi.activity.TransactionDetailsActivity;
import com.example.giambi.model.Category;
import com.example.giambi.model.Transaction;
import com.example.giambi.view.TransactionDetailsView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListSelectionDialog extends DialogFragment {

	public List<String> list = new ArrayList<String>();

	public ListSelectionDialog() {

	}

	public void setList(List<String> data) {
		list = data;
	}

	// public void setDataList(List<String> list){
	// this.list = list;
	// }
	private TransactionDetailsView v;
	private DialogFragment that;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.v("onCreateDialog", "start creating");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.select_dialog, null);
		builder.setView(view);
		ListView listView = (ListView) view.findViewById(R.id.selectionList);
		that = this;
		Category category = new Category();
		this.list = category.categories;
		listView.setAdapter(new MySelectionAdapter<String>(this.getActivity(),
				R.layout.select_item_row, list));
		v = (TransactionDetailsView) this.getActivity();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				v.setCategories(list.get(position));
				that.dismiss();
			}

		});
		Log.v("onCreateDialog", "finished");
		return builder.create();

	}
}
