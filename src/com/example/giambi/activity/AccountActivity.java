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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.giambi.MainActivity;
import com.example.giambi.NewBankAccountDialogFragment;
import com.example.giambi.R;
import com.example.giambi.model.BankAccount;
import com.example.giambi.presenter.AccountPresenter;
import com.example.giambi.util.GetAccountException;
import com.example.giambi.util.Util;
import com.example.giambi.view.AccountView;


public class AccountActivity extends Activity implements AccountView {

    private ListView listView;
    private AccountPresenter accountP;
    private ActionBar actionBar;
    private String loginAccName;
    private List<BankAccount> bankAccounts = new LinkedList<BankAccount>();
    private List<Map<String, Object>> listData = new LinkedList<Map<String, Object>>();
    private DialogFragment dialog;
    private MyAdapter adapter;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        loginAccName = getUsernameFromPreference();
        listView = (ListView) this.findViewById(R.id.account_list);
        actionBar = this.getActionBar();
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        currencyFormat.setMinimumFractionDigits(2);

        setupActionBar();

        accountP = new AccountPresenter(this);

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
        MenuItem[] menuItems = new MenuItem[4];
        menuItems[0] = (MenuItem) menu.findItem(R.id.refresh);
        menuItems[1] = (MenuItem) menu.findItem(R.id.search);
        menuItems[2] = (MenuItem) menu.findItem(R.id.create_new_item);
        menuItems[3] = (MenuItem) menu.findItem(R.id.logout);
        for (MenuItem item : menuItems) {
            item.setOnMenuItemClickListener(accountP.getOnMenuItemClickListener());
        }
        return true;
    }

    @Override
    public void addOnListItemClick(OnItemClickListener l) {
        listView.setOnItemClickListener(l);
    }

    @Override
    public String getUsername() {
        return loginAccName;
    }

    @Override
    public void showAddAccDialog() {
        Log.i("DialogFragment", "show new dialog fragment");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog = new NewBankAccountDialogFragment();

        
        dialog.show(ft, "dialog");
    }

    @Override
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

    /**
     * Flush list view adapter.
     */
    @Override
    public void flushListView() {
        getData(bankAccounts, listData);
        adapter.notifyDataSetChanged();
    }

    /**
     * Get username from preference.
     * @return current username
     */
    private String getUsernameFromPreference() {
        SharedPreferences prefs = this.getSharedPreferences(
                  "com.example.app", Context.MODE_PRIVATE);
        String username = prefs.getString("USERNAME_GIAMBI", null);
        return username;
    }

    private void getData(List<BankAccount> bankAccounts, List<Map<String, Object>> list) {

        // Update back-end BankAccount array
        try {
            int result = BankAccount.getAccouts(loginAccName, bankAccounts);
            if (result == -2) {
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            }
        } catch (GetAccountException e) {
            Log.e("onGetData", e.getMessage());
        }

        // Clear ListView data list
        list.clear();

        // Add entries to ListView data list
        Map<String, Object> map;
        BigDecimal balance;

        for (int i = 0; i < bankAccounts.size(); ++i) {
            map = new HashMap<String, Object>();
            balance = bankAccounts.get(i).getBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);//设置日期格式
            String date = df.format(new Date());//new Date()为获取当前系统时间

            map.put("ID", ((Integer) i).toString());
            map.put("Alias", bankAccounts.get(i).getAlias());
            map.put("BankName", bankAccounts.get(i).getBankName());
            map.put("Balance", currencyFormat.format(balance));
            map.put("Date", date);
            list.add(map);
        }

        System.out.println("bankAccounts array:" + bankAccounts.size());
        System.out.println("listData array:" + list.size());
    }

    /**
     * @author cwl
     * Holder for view in each entry of list.
     */
    private final class ViewHolder{
        protected TextView alias;
        protected TextView bankName;
        protected TextView balance;
        protected TextView date;
    }

    /**
     * Adapter for ListView.
     * @author cwl
     */
    public class MyAdapter extends BaseAdapter{

        private LayoutInflater mInflater;
//        private List<Map<String, Object>> listData;

        public MyAdapter(Context context){
//            listData = v.getListData();
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listData.size();
        }
 
        @Override
        public Object getItem(int arg0) {
            return listData.get(arg0);
        }
 
        @Override
        public long getItemId(int arg0) {
            return Long.parseLong((String) listData.get(arg0).get("ID"));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder=new ViewHolder(); 

                convertView = mInflater.inflate(R.layout.vlist, null);
                holder.alias = (TextView)convertView.findViewById(R.id.bAccount_alias);
                holder.bankName = (TextView)convertView.findViewById(R.id.bAccount_bName);
                holder.balance = (TextView)convertView.findViewById(R.id.bAccount_balance);
                holder.date = (TextView)convertView.findViewById(R.id.bAccount_date);
                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.alias.setText((String)listData.get(position).get("Alias"));
            holder.bankName.setText((String)listData.get(position).get("BankName"));
            holder.balance.setText((String)listData.get(position).get("Balance"));
            holder.date.setText((String)listData.get(position).get("Date"));

            return convertView;
        }
    }


//  private class MyAsyncTask extends AsyncTask{
//
//      @Override
//      protected Object doInBackground(Object... arg0) {
//          // TODO Auto-generated method stub
//          return null;
//      }
//
//      @SuppressWarnings("unchecked")
//      @Override
//      protected void onPostExecute(Object result) {
//          // TODO Auto-generated method stub
//          super.onPostExecute(result);
//      }
//
//      @Override
//      protected void onPreExecute() {
//          // TODO Auto-generated method stub
//          super.onPreExecute();
//      }
//  }


}
