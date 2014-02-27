/**
 * Interface for account activity view.
 */
package com.example.giambi.view;

import java.util.List;
import java.util.Map;

import com.example.giambi.model.BankAccount;
import com.example.giambi.model.LoginAccount;
import com.example.giambi.presenter.AccountPresenter.MyAdapter;

import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cwl
 */
public interface AccountView {

    String getUsername();

    List<BankAccount> getAccounts();

    List<Map<String, Object>> getListData();

    void addOnListItemClick(OnItemClickListener onListItemClickListener);

    void showAddAccDialog();

    void makeTestList();

    LoginAccount getLoginAcc();

    MyAdapter getAdapter();
    
    String getUsernameFromPreference();

}
