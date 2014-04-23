package com.example.giambi.activity;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giambi.InvalidUsernameOrPasswordDialogFragment;
import com.example.giambi.NewBankAccountDialogFragment;
import com.example.giambi.R;
import com.example.giambi.model.BankAccount;
import com.example.giambi.presenter.AccountPresenter;
import com.example.giambi.util.Util;
import com.example.giambi.view.AccountView;

/**
 * account activity.
 */
public class AccountActivity extends NavigationDrawerActivity implements AccountView {

    /**
     * list view.
     */
    private ListView listView;
    /**
     * presenter.
     */
    private AccountPresenter accountPresenter;
    /**
     * action bar.
     */
    private ActionBar actionBar;
    /**
     * login account name.
     */
    private String loginAccName;
    /**
     * list data.
     */
    private List<Map<String, String>> listData = new LinkedList<Map<String, String>>();
    /**
     * dialog fragment.
     */
    private DialogFragment dialog;
    /**
     * adapter.
     */
    private MyAdapter adapter;
    /**
     * current format.
     */
    private NumberFormat currencyFormat = NumberFormat
            .getCurrencyInstance(Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginAccName = getUsernameFromPreference();
        listView = (ListView) this.findViewById(R.id.account_list);
        actionBar = this.getActionBar();
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        currencyFormat.setMinimumFractionDigits(2);
        accountPresenter = new AccountPresenter(this);
        setupActionBar();

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
        final int num = 4;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.accountactivity_menu_options, menu);
        MenuItem[] menuItems = new MenuItem[num];
        menuItems[0] = (MenuItem) menu.findItem(R.id.account_refresh);
        menuItems[1] = (MenuItem) menu.findItem(R.id.account_search);
        menuItems[2] = (MenuItem) menu.findItem(R.id.account_create_new_item);
        menuItems[num - 1] = (MenuItem) menu.findItem(R.id.account_logout);
        for (MenuItem item : menuItems) {
            item.setOnMenuItemClickListener(accountPresenter
                    .getOnMenuItemClickListener());
        }
        return true;
    }

    @Override
    public void addOnListItemClick(OnItemClickListener l) {
        listView.setOnItemClickListener(l);
    }

    @Override
    public final String getUsername() {
        return loginAccName;
    }

    @Override
    public final void showAddAccDialog() {
        Log.i("DialogFragment", "show new dialog fragment");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog = new NewBankAccountDialogFragment();

        dialog.show(ft, "dialog");
    }

    @Override
    public final void setDialogMessage(int errorCode) {
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
        default:
            break;
        }
        DialogFragment dialog1 = new InvalidUsernameOrPasswordDialogFragment();
        dialog1.setArguments(bundle);
        dialog1.show(ft, "dialog");
    }

    /**
     * Flush list view adapter.
     * 
     * @param bankAccounts
     *            bank accounts
     */
    @Override
    public final void setAccountList(List<BankAccount> bankAccounts) {
        mapBankAccountData(bankAccounts, listData);
        adapter.notifyDataSetChanged();
    }

    /**
     * Get username from preference.
     * 
     * @return current username
     */
    private String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences("com.example.app",
                Context.MODE_PRIVATE);
        String username = prefs.getString("USERNAME_GIAMBI", null);
        return username;
    }

    /**
     * map data to list.
     * 
     * @param bankAccounts
     *            bank accounts
     * @param list
     *            list data
     */
    private void mapBankAccountData(List<BankAccount> bankAccounts,
            List<Map<String, String>> list) {
        // Update back-end BankAccount array

        // Clear ListView data list
        list.clear();

        // Add entries to ListView data list
        Map<String, String> map;
        BigDecimal balance;

        for (int i = 0; i < bankAccounts.size(); ++i) {
            map = new HashMap<String, String>();
            balance = bankAccounts.get(i).getBalance()
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String date = df.format(new Date());

            map.put("ID", ((Integer) i).toString());
            map.put("Alias", bankAccounts.get(i).getAlias());
            map.put("BankName", bankAccounts.get(i).getBankName());
            map.put("Balance", currencyFormat.format(balance));
            map.put("Date", date);
            list.add(map);
        }

        // System.out.println("bankAccounts array:" + bankAccounts.size());
        // System.out.println("listData array:" + list.size());
    }

    /**
     * @author cwl Holder for view in each entry of list.
     */
    private final class ViewHolder {
        /**
         * alias.
         */
        private TextView alias;
        /**
         * bank name.
         */
        private TextView bankName;
        /**
         * balance.
         */
        private TextView balance;
        /**
         * date.
         */
        private TextView date;
    }

    /**
     * Adapter for ListView.
     * 
     * @author cwl
     */
    public class MyAdapter extends BaseAdapter {

        /**
         * inflater.
         */
        private LayoutInflater mInflater;

        /**
         * constructor.
         * 
         * @param context
         *            context
         */
        public MyAdapter(Context context) {
            // listData = v.getListData();
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public final int getCount() {
            return listData.size();
        }

        @Override
        public final Object getItem(int arg0) {
            return listData.get(arg0);
        }

        @Override
        public final long getItemId(int arg0) {
            return Long.parseLong((String) listData.get(arg0).get("ID"));
        }

        @Override
        public final View getView(int position, View convertView,
                ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.vlist, null);
                holder.alias = (TextView) convertView
                        .findViewById(R.id.bAccount_alias);
                holder.bankName = (TextView) convertView
                        .findViewById(R.id.bAccount_bName);
                holder.balance = (TextView) convertView
                        .findViewById(R.id.bAccount_balance);
                holder.date = (TextView) convertView
                        .findViewById(R.id.bAccount_date);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.alias.setText((String) listData.get(position).get("Alias"));
            holder.bankName.setText((String) listData.get(position).get(
                    "BankName"));
            holder.balance.setText((String) listData.get(position).get(
                    "Balance"));
            holder.date.setText((String) listData.get(position).get("Date"));

            return convertView;
        }
    }

    @Override
    public final void startTransactionPage(String accountNumber) {
        Intent i = new Intent(this, TransactionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("AccountNumber", accountNumber);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public final AccountPresenter getPresenter() {
        return this.accountPresenter;
    }

    @Override
    protected void setupView() {
        setContentView(R.layout.account_page);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.account_page_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_bar_account);
    }

}
