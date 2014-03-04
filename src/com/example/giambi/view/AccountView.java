/**
 * Interface for account activity view.
 */
package com.example.giambi.view;

import java.util.List;

import com.example.giambi.model.BankAccount;

import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cwl
 */
public interface AccountView {

    String getUsername();

    void addOnListItemClick(OnItemClickListener onListItemClickListener);

    void showAddAccDialog();

    void setAccountList(List<BankAccount> bankAccounts);

    void setDialogMessage(int errorCode);

    void startTransactionPage(String accountNumber);
}
