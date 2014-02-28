package com.example.giambi.presenter;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.view.AccountView;

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

        v.flushListView();
        v.addOnListItemClick(this.onListItemClickListener);
        Log.v("AccountPresenter", "Listeners set up complete.");
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return this.onMenuItemClickListener;
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
