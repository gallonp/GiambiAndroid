/**
 * Interface for account activity view.
 */
package com.example.giambi.view;

import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cwl
 */
public interface AccountView {

    String getUsername();

    void addOnListItemClick(OnItemClickListener onListItemClickListener);

    void showAddAccDialog();

    void flushListView();

    void setDialogMessage(int errorCode);

    void startTransactionPage(String accountNumber);
}
