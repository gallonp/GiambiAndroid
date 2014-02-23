package com.example.giambi.presenter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.giambi.R;
import com.example.giambi.model.BankAccount;
import com.example.giambi.view.AccountView;

/**
 * Presenter for account activity.
 * @author cwl
 *
 */
public class AccountPresenter {

    private AccountView v;
    private List<BankAccount> bankAccounts;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private BigDecimal balance;

    /**
     * Constructor.
     */
    public AccountPresenter(AccountView view) {
        this.v = view;
        currencyFormat.setMinimumFractionDigits(2);
        bankAccounts = v.getAccounts();
//        v.setListData(getData());
        getData(v.getListData());
        v.addOnListItemClick(this.onListItemClickListener);
        Log.v("AccountPresenter", "Listeners set up complete.");
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return this.onMenuItemClickListener;
    }

    public void updateListData() {
        bankAccounts = v.getAccounts();
//        v.setListData(getData(v.getListData()));
        getData(v.getListData());
    }

    private void getData(List<Map<String, Object>> list) {
        v.makeTestList();
        list.clear();
        
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> map;

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

        System.out.println("bankAccounts array:" + v.getAccounts().size());
        System.out.println("listData array:" + v.getListData().size());
//        return list;
    }

    private OnItemClickListener onListItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            // TODO 自动生成的方法存根
            
        }
    };

    private OnMenuItemClickListener onMenuItemClickListener =
            new OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String itemTitle = item.getTitle().toString();
                    if (itemTitle.equals("Refresh")) {
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
        private List<Map<String, Object>> listData;

        public MyAdapter(Context context){
            listData = v.getListData();
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

}
