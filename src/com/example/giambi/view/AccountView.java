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

    /**
     * get username.
     * @return username
     */
    String getUsername();

    /**
     * add on list item click listener.
     * @param onListItemClickListener onListItemClickListener
     */
    void addOnListItemClick(OnItemClickListener onListItemClickListener);

    /**
     * show dialog.
     */
    void showAddAccDialog();

    /**
     * set account list.
     * @param bankAccounts bank accounts
     */
    void setAccountList(List<BankAccount> bankAccounts);

    /**
     * set dialog message.
     * @param errorCode error code
     */
    void setDialogMessage(int errorCode);

    /**
     * start transaction.
     * @param accountNumber account number
     */
    void startTransactionPage(String accountNumber);

    /**
     * get presenter.
     * @return presenter
     */
    AccountPresenter getPresenter();
}
