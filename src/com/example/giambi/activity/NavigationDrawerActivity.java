package com.example.giambi.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.giambi.AccountListDialog;
import com.example.giambi.AccountListDialog.AccountDialogListener;
import com.example.giambi.DatePickerDialogFragment;
import com.example.giambi.IfCreateNewAccountDialog;
import com.example.giambi.IfCreateNewAccountDialog.DialogListener;
import com.example.giambi.MainActivity;
import com.example.giambi.NewBankAccountDialogFragment;
import com.example.giambi.R;
import com.example.giambi.model.BankAccount;
import com.example.giambi.util.GetAccountException;

public abstract class NavigationDrawerActivity extends Activity implements
        DialogListener, AccountDialogListener {

    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected ActionBarDrawerToggle mDrawerToggle;

    protected CharSequence mDrawerTitle;
    protected CharSequence mTitle;
    protected String[] drawerTitles;

    private ArrayAdapter<String> mArrayAdapter;
    private List<BankAccount> accounts;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        // view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets up the view here.
        setupView();
        mTitle = mDrawerTitle = getTitle();
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.RELATIVE_LAYOUT_DIRECTION);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
        mDrawerLayout, /* DrawerLayout object */
        R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
        R.string.drawer_open, /* "open drawer" description for accessibility */
        R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                                         // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                                         // onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Sets the content view. call setcontentView here.
     */
    protected abstract void setupView();

    protected void selectItem(int position) {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (position) {
        case 0:
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
            break;
        case 1:
            Log.i("DialogFragment", "show new dialog fragment");
            DialogFragment dialog = new NewBankAccountDialogFragment();
            dialog.show(ft, "dialog");
            break;
        case 2:
            SharedPreferences prefs = this.getSharedPreferences(
                    "com.example.app", Context.MODE_PRIVATE);
            String username = prefs.getString("USERNAME_GIAMBI", null);
            accounts = new ArrayList<BankAccount>();
            try {
                BankAccount.getAccounts(username, accounts);
            } catch (GetAccountException e) {
                e.printStackTrace();
                Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT);
            }
            if (accounts.isEmpty()) {
                DialogFragment dialog2 = new IfCreateNewAccountDialog();
                dialog2.show(getFragmentManager(), "dialog");
            } else if (accounts.size() == 1) {
                accountSelected(0);
            } else {
                mArrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1);
                for (BankAccount b : accounts) {
                    mArrayAdapter.add(b.getBankName() + ":\n"
                            + b.getAccountNum());
                }
                DialogFragment dialog21 = new AccountListDialog();
                dialog21.show(getFragmentManager(), "dialog");
            }
            break;
        case 3:
            Log.i("DialogFragment", "show new dialog fragment");
            DialogFragment dialog4 = new DatePickerDialogFragment(this);
            dialog4.setArguments(null);
            dialog4.show(ft, "dialog");
            break;
        case 4:
            Intent intent5 = new Intent();
            intent5.setClass(this, RegisterActivity.class);
            startActivity(intent5);
            break;
        case 5:
            Intent intent6 = new Intent(this, MainActivity.class);
            Toast.makeText(this, "Logged out.", Toast.LENGTH_SHORT).show();
            startActivity(intent6);
            break;
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            selectItem(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void positiveChoice() {
        selectItem(1);
    }

    @Override
    public ArrayAdapter<String> getArrayAdapter() {
        return mArrayAdapter;
    }

    @Override
    public void accountSelected(int i) {
        Intent intent = new Intent(this, TransactionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("AccountNumber", accounts.get(i).getAccountNum());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
