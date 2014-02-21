package com.example.giambi.activity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giambi.R;
import com.example.giambi.model.BankAccount;
import com.example.giambi.model.LoginAccount;
import com.example.giambi.presenter.AccountPresenter;
import com.example.giambi.view.AccountView;

public class AccountActivity extends Activity implements AccountView {

    private ListView listView;
    private AccountPresenter accountP;
    private ActionBar actionBar;
    private MenuItem[] menuItems = new MenuItem[4];
    private LoginAccount loginAcc;
    private List<BankAccount> bankAccounts = new LinkedList<BankAccount>();
    private List<Map<String, Object>> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        listView = (ListView) this.findViewById(R.id.account_list);
        actionBar = this.getActionBar();
        setupActionBar();
        accountP = new AccountPresenter(this);
        LoginAccount loginAcc = (LoginAccount) getIntent().getParcelableExtra("LoginAccount");
        Log.i(ACTIVITY_SERVICE, "Account Acitivity initialize complete.");
        MyAdapter adapter = new MyAdapter(this);
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
    public List<Map<String, Object>> getListData() {
        return this.listData;
    }

    @Override
    public void setListData(List<Map<String, Object>> list) {
        this.listData = list;
    }

    protected final class ViewHolder{
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

        public MyAdapter(Context context){
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


    private void addOnOptionsItemSelected(OnMenuItemClickListener listener) {
        for (MenuItem item : menuItems) {
            item.setOnMenuItemClickListener(listener);
        }
    }

}
