package com.example.giambi.presenter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

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
        v.setListData(getData());
        v.addOnListItemClick(this.onListItemClickListener);
        Log.v("AccountPresenter", "Listeners set uo complete.");
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return this.onMenuItemClickListener;
    }

    private List<Map<String, Object>> getData() {
        makeTestList();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
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

        return list;
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


    private void makeTestList() {
        bankAccounts.add(new BankAccount("JOINT", "MAMI", "901938278", "12938.90"));
        bankAccounts.add(new BankAccount("unfinished", "KTK", "34022934798", "1"));
        bankAccounts.add(new BankAccount("One", "ALTIMA", "0112389872", "5.002"));
    }
}
