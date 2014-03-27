package com.example.giambi.view;

import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.giambi.model.Transaction;

public interface TransactionDetailsView {

    void AddOnClickListener(OnClickListener clickerListener);

    Transaction getCurrentTransactionData();

    String getUsernameFromPreference();

    void finish();

    void setCategories(String categories);

    void addOnItemClickListener(OnItemClickListener listener, ListView list);

}
