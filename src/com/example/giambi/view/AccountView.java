/**
 * Interface for account activity view.
 */
package com.example.giambi.view;

import java.util.List;
import java.util.Map;

import com.example.giambi.model.BankAccount;

import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cwl
 */
public interface AccountView {

    String getUsername();

    List<BankAccount> getAccounts();

    List<Map<String, Object>> getListData();

    void setListData(List<Map<String, Object>> list);

    void addOnListItemClick(OnItemClickListener onListItemClickListener);

    void showAddAccDialog();

}
