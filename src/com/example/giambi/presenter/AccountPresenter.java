package com.example.giambi.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.MainActivity;
import com.example.giambi.activity.LoginActivity;
import com.example.giambi.model.BankAccount;
import com.example.giambi.util.GetAccountException;
import com.example.giambi.view.AccountView;

import java.util.List;

/**
 * Presenter for account activity.
 * @author cwl
 *
 */
public class AccountPresenter {

    private AccountView v;

    /**
     * Constructor.
     */
    public AccountPresenter(AccountView view) {
        this.v = view;

        v.addOnListItemClick(this.onListItemClickListener);
        Log.v("AccountPresenter", "Listeners set up complete.");
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return this.onMenuItemClickListener;
    }

    public void getAccounts(String loginAccName,
                            List<BankAccount> bankAccounts) {
        try {
            int result = BankAccount.getAccouts(loginAccName, bankAccounts);
            if (result == -2) {
                Intent intent = new Intent();
                intent.setClass((Context) v, LoginActivity.class);
                ((Context) v).startActivity(intent);
                ((Activity) v).finish();
            }
        } catch (GetAccountException e) {
            Log.e("onGetData", e.getMessage());
        }
    }
    /**
     * Listener for listView item click
     */
    private OnItemClickListener onListItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            // TODO 自动生成的方法存根
            
        }
    };

    /**
     * Listener for Menu Item click
     */
    private OnMenuItemClickListener onMenuItemClickListener =
            new OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String itemTitle = item.getTitle().toString();
                    if (itemTitle.equals("Refresh")) {
                        v.flushListView();
                        Log.i("MenuItem", "1");
                    } else if (itemTitle.equals("Search")) {
                        Log.i("MenuItem", "2");
                    } else if (itemTitle.equals("New..")) {
                        Log.i("MenuItem", "3");
                        v.showAddAccDialog();
                    } else if (itemTitle.equals("Log Out")) {
                        Log.i("MenuItem", "4");
                    } else {
                        Log.i("MenuItem", "Unknown");
                    }
                    return false;
                }

    };

}
