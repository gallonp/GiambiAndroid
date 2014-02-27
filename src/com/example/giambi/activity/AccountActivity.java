package com.example.giambi.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.giambi.InvalidUsernameOrPasswordDialogFragment;
import com.example.giambi.NewBankAccountDialogFragment;
import com.example.giambi.NewBankAccountDialogFragment.EditDialogListener;
import com.example.giambi.R;
import com.example.giambi.model.BankAccount;
import com.example.giambi.model.LoginAccount;
import com.example.giambi.presenter.AccountPresenter;
import com.example.giambi.presenter.AccountPresenter.MyAdapter;
import com.example.giambi.util.RegisterException;
import com.example.giambi.util.Util;
import com.example.giambi.view.AccountView;

public class AccountActivity extends Activity implements 
     AccountView, EditDialogListener{

    private ListView listView;
    private AccountPresenter accountP;
    private ActionBar actionBar;
    private MenuItem[] menuItems = new MenuItem[4];
    private LoginAccount loginAcc;
    private List<BankAccount> bankAccounts = new LinkedList<BankAccount>();
    private List<Map<String, Object>> listData = new LinkedList<Map<String, Object>>();
    private DialogFragment dialog;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        loginAcc = (LoginAccount) getIntent().getParcelableExtra("LoginAccount");
        listView = (ListView) this.findViewById(R.id.account_list);
        actionBar = this.getActionBar();
        setupActionBar();

        accountP = new AccountPresenter(this);
        adapter = accountP.new MyAdapter(this);
        listView.setAdapter(adapter);
        
        Log.i(ACTIVITY_SERVICE, "BankAccount info acquired complete.");
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu_options, menu);
        menuItems[0] = (MenuItem) menu.findItem(R.id.refresh);
        menuItems[1] = (MenuItem) menu.findItem(R.id.search);
        menuItems[2] = (MenuItem) menu.findItem(R.id.create_new_item);
        menuItems[3] = (MenuItem) menu.findItem(R.id.logout);
        addOnOptionsItemSelected(accountP.getOnMenuItemClickListener());
        return true;
    }

    @Override
    public void addOnListItemClick(OnItemClickListener l) {
        listView.setOnItemClickListener(l);
    }

    @Override
    public String getUsername() {
        return loginAcc.getUsername();
    }

    @Override
    public List<BankAccount> getAccounts() {
        return this.bankAccounts;
    }

    @Override
    public LoginAccount getLoginAcc() {
        return this.loginAcc;
    }

    @Override
    public List<Map<String, Object>> getListData() {
        return this.listData;
    }

    @Override
    public MyAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void showAddAccDialog() {
        Log.i("DialogFragment", "show new dialog fragment");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        Bundle bundle = new Bundle();
        dialog = new NewBankAccountDialogFragment();

//        dialog.setArguments(bundle);
        dialog.show(ft, "dialog");
    }

    /**
     * Callback of AccountPresenter to set up menu item listeners.
     * @param listener menu item click listener
     */
    private void addOnOptionsItemSelected(OnMenuItemClickListener listener) {
        for (MenuItem item : menuItems) {
            item.setOnMenuItemClickListener(listener);
        }
    }

    /**
     * Update the accounts in listView.
     */
    @Override
    public void updateResult(String[] inputText) {
        if (Util.isNumeric(inputText[2])) {
            try {
                new BigDecimal(inputText[3]);
            } catch (Exception e) {
                if (dialog != null) dialog.dismiss();
                setDialogMessage(Util.INVALID_BALANCE);
                Log.i("onAddBankAccount", e.getMessage());
                return;
            }
        } else {
            if (dialog != null) dialog.dismiss();
            setDialogMessage(Util.INVALID_ACCOUNT_NUMBER);
            return;
        }
        BankAccount newAcc = new BankAccount(loginAcc, inputText[0], inputText[1], inputText[2], inputText[3]);
//        bankAccounts.add(newAcc);
        try {
            newAcc.addToServer();
        } catch (RegisterException e) {
            Log.i("onAddBankAccount", e.getMessage());
        }

        accountP.updateListData();
    }

    public void setDialogMessage(int errorCode) {
        Log.e("BankAccountCreateError", "Invalid information.");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        switch (errorCode) {
        case Util.INVALID_ACCOUNT_NUMBER:
            bundle.putString("message",
                    getString(R.string.dialog_message_invalid_account_number));
            break;
        case Util.INVALID_BALANCE:
            bundle.putString("message",
                    getString(R.string.dialog_message_invalid_balance));
            break;
        }
        DialogFragment dialog = new InvalidUsernameOrPasswordDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(ft, "dialog");
    }

//    private class MyAsyncTask extends AsyncTask{
//
//        @Override
//        protected Object doInBackground(Object... arg0) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//        @SuppressWarnings("unchecked")
//        @Override
//        protected void onPostExecute(Object result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//        }
//    }

    public void makeTestList() {
        if (bankAccounts.size() == 0) {
            bankAccounts.add(new BankAccount(loginAcc, "JOINT", "MAMI", "901938278", "12938.90"));
            bankAccounts.add(new BankAccount(loginAcc, "unfinished", "KTK", "34022934798", "1"));
            bankAccounts.add(new BankAccount(loginAcc, "One", "ALTIMA", "0112389872", "5.002"));
        }
    }

	@Override
	public String getUsernameFromPreference() {
		SharedPreferences prefs = this.getSharedPreferences(
			      "com.example.app", Context.MODE_PRIVATE);
		String username = prefs.getString("USERNAME_GIAMBI", null);
		return username;
	}

}
