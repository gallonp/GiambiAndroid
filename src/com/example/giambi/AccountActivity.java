package com.example.giambi;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.giambi.presenter.AccountPresenter;
import com.example.giambi.view.AccountView;

public class AccountActivity extends Activity implements AccountView {

    private ListView listView;
    private MenuItem[] menuItems = new MenuItem[4];
    private AccountPresenter account;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        // Show the Up button in the action bar.
        setupActionBar();
        listView = (ListView) this.findViewById(R.id.account_list);
        menuItems[0] = (MenuItem) this.findViewById(R.id.refresh);
        menuItems[1] = (MenuItem) this.findViewById(R.id.search);
        menuItems[2] = (MenuItem) this.findViewById(R.id.create_new_item);
        menuItems[3] = (MenuItem) this.findViewById(R.id.logout);
        actionBar = this.getActionBar();
        account = new AccountPresenter(this);
        Log.i(ACTIVITY_SERVICE, "Account Acitivity initialize complete.");

    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void addOnListItemClick(OnClickListener l) {
        listView.setOnClickListener(l);
    }

    @Override
    public void addOnOptionsItemSelected(OnMenuItemClickListener listener) {
        for (MenuItem item : menuItems) {
            item.setOnMenuItemClickListener(listener);
        }
    }
    
}
