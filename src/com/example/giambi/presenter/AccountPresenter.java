package com.example.giambi.presenter;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MenuItem.OnMenuItemClickListener;

import com.example.giambi.view.AccountView;
import com.example.giambi.util.Util;

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
        v.addOnOptionsItemSelected(onMenuItemClickListener);
        Log.v("AccountPresenter", "Listens set uo complete.");
    }

    private OnClickListener onListItemClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            return;
        }
        
    };

    private OnMenuItemClickListener onMenuItemClickListener =
            new OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int itemNum = item.getItemId();
                    switch (itemNum) {
                    case 1:
                        Log.i("MunuItem", "1");
                        break;
                    case 2:
                        Log.i("MunuItem", "2");
                        break;
                    case 3:
                        Log.i("MunuItem", "3");
                        break;
                    case 4:
                        Log.i("MunuItem", "4");
                        break;
                    default:
                        Log.i("MunuItem", "Unknown");
                        break;
                    }
                    return false;
                }
        
    };
}
