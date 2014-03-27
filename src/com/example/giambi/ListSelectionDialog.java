package com.example.giambi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.giambi.model.Category;
import com.example.giambi.view.TransactionDetailsView;

import java.util.ArrayList;
import java.util.List;

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
