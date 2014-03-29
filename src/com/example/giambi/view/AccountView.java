/**
 * Interface for account activity view.
 */
package com.example.giambi.view;

import android.widget.AdapterView.OnItemClickListener;
import com.example.giambi.model.BankAccount;
import com.example.giambi.presenter.AccountPresenter;

import java.util.List;

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

    AccountPresenter getPresenter();
}
